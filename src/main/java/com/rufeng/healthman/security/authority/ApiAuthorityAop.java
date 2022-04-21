package com.rufeng.healthman.security.authority;

import com.rufeng.healthman.exceptions.AccessDeniedException;
import com.rufeng.healthman.exceptions.AuthenticationException;
import com.rufeng.healthman.security.authentication.Authentication;
import com.rufeng.healthman.security.context.SecurityContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author rufeng
 * @time 2022-04-21 18:59
 * @package com.rufeng.healthman.security.authority
 * @description TODO
 */
@Aspect
@Component
@Order(2)
public class ApiAuthorityAop {
    @Before("@annotation(io.swagger.v3.oas.annotations.Operation) && (@within(ApiAuthority) || @annotation(ApiAuthority))")
    public void hasAuthority(JoinPoint jp) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationException("未认证！");
        }
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        Operation operation = method.getAnnotation(Operation.class);
        if (!authentication.getUserInfo().getAuthorities().contains(operation.operationId())) {
            throw new AccessDeniedException(String.format("没有 %s 的访问权限", operation.summary()));
        }
    }
}
