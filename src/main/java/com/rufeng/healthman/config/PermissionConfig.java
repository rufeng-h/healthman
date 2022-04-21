package com.rufeng.healthman.config;

import com.rufeng.healthman.mapper.PtOperationMapper;
import com.rufeng.healthman.pojo.ptdo.PtOperation;
import com.rufeng.healthman.security.authority.ApiAuthority;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-04-19 17:06
 * @package com.rufeng.healthman.config
 * @description TODO
 */
@Configuration
@Slf4j
public class PermissionConfig {
    public PermissionConfig(RequestMappingHandlerMapping handlerMapping, PtOperationMapper ptOperationMapper) {
        Map<RequestMappingInfo, HandlerMethod> methodMap = handlerMapping.getHandlerMethods();
        Set<Map.Entry<RequestMappingInfo, HandlerMethod>> entries = methodMap.entrySet();
        List<PtOperation> list = new ArrayList<>();
        List<String> ls = new ArrayList<>();
        Set<String> opers = ptOperationMapper.list().stream().map(PtOperation::getOperId).collect(Collectors.toSet());
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : entries) {
            Method method = entry.getValue().getMethod();
            ApiAuthority authority = method.getDeclaringClass().getAnnotation(ApiAuthority.class);
            Operation operation = method.getAnnotation(Operation.class);
            if (authority == null || operation == null) {
                String methodId = method.getDeclaringClass().getSimpleName() + "." + method.getName();
                log.debug(String.format("%s没有权限控制", methodId));
            } else {
                if (!opers.contains(operation.operationId())) {
                    list.add(PtOperation.builder()
                            .operId(operation.operationId())
                            .operSummary(operation.summary())
                            .build());
                }
                String s = Arrays.stream(operation.operationId().split(":")).map(String::toUpperCase).collect(Collectors.joining("_"));
                ls.add(String.format("export const %s = \"%s\";", s, operation.operationId()));
            }
        }
        if (list.size() > 0) {
            ptOperationMapper.batchInsertSelective(list);
        }
        ls.sort(String::compareTo);
        System.out.println(String.join("\n", ls));
    }
}
