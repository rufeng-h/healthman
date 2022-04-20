package com.rufeng.healthman.config;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @author rufeng
 * @time 2022-04-19 17:06
 * @package com.rufeng.healthman.config
 * @description TODO
 */
@Configuration
public class PermissionConfig {
    public PermissionConfig(RequestMappingHandlerMapping handlerMapping){
        Map<RequestMappingInfo, HandlerMethod> methodMap = handlerMapping.getHandlerMethods();
        Set<Map.Entry<RequestMappingInfo, HandlerMethod>> entries = methodMap.entrySet();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : entries) {
            RequestMappingInfo mappingInfo = entry.getKey();
            System.out.println(mappingInfo.getPatternValues() + ":" + entry.getValue());
            Method method = entry.getValue().getMethod();
            Operation annotation = method.getAnnotation(Operation.class);
            if (annotation != null) {
                System.out.println(annotation.operationId());
            }
        }
    }
}
