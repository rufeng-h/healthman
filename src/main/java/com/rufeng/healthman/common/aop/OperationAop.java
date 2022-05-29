package com.rufeng.healthman.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.enums.OperTypeEnum;
import com.rufeng.healthman.pojo.ptdo.PtOperLog;
import com.rufeng.healthman.security.support.UserInfo;
import com.rufeng.healthman.service.PtCommonService;
import com.rufeng.healthman.service.PtOperLogService;
import io.swagger.v3.oas.annotations.Operation;
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
 * @description 操作日志切面
 */
@Aspect
@Component
@Order(1)
public class OperationAop {
    private final PtOperLogService ptOperLogService;
    private final ObjectMapper objectMapper;
    private final PtCommonService ptCommonService;

    public OperationAop(PtOperLogService ptOperLogService, ObjectMapper objectMapper, PtCommonService ptCommonService) {
        this.ptOperLogService = ptOperLogService;
        this.objectMapper = objectMapper;
        this.ptCommonService = ptCommonService;
    }

    @Around("@annotation(OperLogRecord)")
    public Object operLog(ProceedingJoinPoint pjp) throws Throwable {
        PtOperLog operLog = null;
        if (ptCommonService.isLogin()) {
            UserInfo userInfo = ptCommonService.getUserInfo();
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            OperLogRecord logRecord = method.getAnnotation(OperLogRecord.class);
            if (logRecord != null) {
                Operation operation = method.getAnnotation(Operation.class);
                operLog = new PtOperLog();
                operLog.setOperMethod(method.getName());
                operLog.setOperUserId(userInfo.getUserId());
                operLog.setOperUserName(userInfo.getUsername());
                operLog.setOperUserType(userInfo.getUserType());
                if (!StringUtils.isEmptyOrBlank(logRecord.value())) {
                    operLog.setOperDesc(logRecord.value());
                } else if (operation != null) {
                    operLog.setOperDesc(operation.summary());
                }
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (requestAttributes != null) {
                    HttpServletRequest request = requestAttributes.getRequest();
                    operLog.setOperType(httpMethod2OperTypeEnum(request.getMethod()));
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

    private OperTypeEnum httpMethod2OperTypeEnum(String method) {
        String m = method.toUpperCase();
        switch (m) {
            case "PUT":
                return OperTypeEnum.UPDATE;
            case "DELETE":
                return OperTypeEnum.DELETE;
            case "POST":
                return OperTypeEnum.INSERT;
            default:
                return OperTypeEnum.OTHER;
        }
    }
}
