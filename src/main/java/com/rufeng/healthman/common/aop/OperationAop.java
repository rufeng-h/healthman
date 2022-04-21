package com.rufeng.healthman.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rufeng.healthman.pojo.ptdo.PtOperLog;
import com.rufeng.healthman.service.PtCommonService;
import com.rufeng.healthman.service.PtOperLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author rufeng
 * @time 2022-04-19 11:35
 * @package com.rufeng.healthman.common.aop
 * @description TODO
 */
@Aspect
@Component
@Order(1)
public class OperationAop {
    private final PtOperLogService ptOperLogService;
    private final ObjectMapper objectMapper;
    private final PtCommonService ptCommonService;

    public OperationAop(PtOperLogService ptOperLogService,
                        ObjectMapper objectMapper,
                        PtCommonService ptCommonService) {
        this.ptOperLogService = ptOperLogService;
        this.objectMapper = objectMapper;
        this.ptCommonService = ptCommonService;
    }

    @Around("@annotation(OperLogRecord)")
    public Object operLog(ProceedingJoinPoint pjp) throws Throwable {
        PtOperLog operLog = null;
        if (ptCommonService.isLogin()) {
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            OperLogRecord operation = method.getAnnotation(OperLogRecord.class);
            if (operation != null) {
                operLog = new PtOperLog();
                operLog.setOperMethod(method.getName());
                operLog.setOperAdminId(ptCommonService.getCurrentUserId());
                operLog.setOperAdminName(ptCommonService.getCurrentUserName());
                operLog.setOperDesc(operation.description());
                operLog.setOperType(operation.operType());
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (requestAttributes != null) {
                    HttpServletRequest request = requestAttributes.getRequest();
                    operLog.setOperUri(request.getRequestURI());
                    operLog.setOperIp(request.getRemoteAddr());
                    operLog.setOperReqParam(objectMapper.writeValueAsString(request.getParameterMap()));
                }
            }
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            if (operLog != null) {
                operLog.setOperExp(throwable.getMessage());
                operLog.setOperStatus(0);
            }
            throw throwable;
        } finally {
            if (operLog != null) {
                ptOperLogService.addLog(operLog);
            }
        }
    }
}
