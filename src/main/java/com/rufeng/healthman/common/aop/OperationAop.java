package com.rufeng.healthman.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rufeng.healthman.common.util.StringUtils;
import com.rufeng.healthman.enums.OperTypeEnum;
import com.rufeng.healthman.pojo.ptdo.PtOperLog;
import com.rufeng.healthman.security.support.UserInfo;
import com.rufeng.healthman.service.PtCommonService;
import com.rufeng.healthman.service.PtOperLogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rufeng
 * @time 2022-04-19 11:35
 * @package com.rufeng.healthman.common.aop
 * @description 操作日志切面
 */
@Aspect
@Component
@Order(1)
@Slf4j
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

                if (!StringUtils.isEmptyOrBlank(logRecord.spel())) {
                    SpelExpressionParser parser = new SpelExpressionParser();
                    EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
                    for (Map.Entry<String, Object> entry : resolveParams(method, pjp).entrySet()) {
                        context.setVariable(entry.getKey(), entry.getValue());
                    }

                    log.info(parser.parseExpression(logRecord.spel()).getValue(context,String.class));
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

    private Map<String, Object> resolveParams(Method method, ProceedingJoinPoint pjp) {
        Parameter[] parameters = method.getParameters();
        Map<String, Object> map = new HashMap<>();
        Object[] args = pjp.getArgs();
        for (int i = 0; i < parameters.length; i++){
            map.put(parameters[i].getName(), args[i]);
        }
        return map;
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
