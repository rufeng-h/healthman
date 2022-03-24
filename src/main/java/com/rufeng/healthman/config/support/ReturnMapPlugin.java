package com.rufeng.healthman.config.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rufeng
 */
@Component
@Intercepts(@Signature(type = ResultSetHandler.class,
        method = "handleResultSets", args = Statement.class))
@Slf4j
public class ReturnMapPlugin implements Interceptor {
    private final Map<String, Method> methodCache = new HashMap<>(10);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MetaObject metaObject = SystemMetaObject.forObject(invocation.getTarget());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("mappedStatement");
        String methodId = mappedStatement.getId();
        Method method = findMethod(methodId);
        if (method == null) {
            return invocation.proceed();
        }
        ParameterizedType returnType = (ParameterizedType) method.getGenericReturnType();
        TypeHandlerRegistry registry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
        Statement statement = (Statement) invocation.getArgs()[0];
        ResultSet resultSet = statement.getResultSet();
        Class<?> keyType = (Class<?>) returnType.getActualTypeArguments()[0];
        Class<?> valueType = (Class<?>) returnType.getActualTypeArguments()[1];
        TypeHandler<?> keyTypeHandler = registry.getTypeHandler(keyType);
        TypeHandler<?> valueTypeHandler = registry.getTypeHandler(valueType);
        List<Object> list = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>(10);
        while (resultSet.next()) {
            Object key = keyTypeHandler.getResult(resultSet, 1);
            Object value = valueTypeHandler.getResult(resultSet, 2);
            map.put(key, value);
        }
        methodCache.put(methodId, method);
        list.add(map);
        return list;
    }

    private Method findMethod(String methodId) throws ClassNotFoundException {
        if (methodCache.containsKey(methodId)) {
            return methodCache.get(methodId);
        }
        int i = methodId.lastIndexOf(".");
        String clsName = methodId.substring(0, i);
        String methodName = methodId.substring(i + 1);
        Class<?> searchType = Class.forName(clsName);
        while (searchType != null) {
            Method[] methods = searchType.getMethods();
            for (Method method : methods) {
                if (methodName.equals(method.getName()) && method.getAnnotation(ReturnMap.class) != null) {
                    ParameterizedType returnType = (ParameterizedType) method.getGenericReturnType();
                    if (!Map.class.equals(returnType.getRawType())) {
                        throw new IllegalArgumentException("必须返回Map类型!");
                    }
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }
}
