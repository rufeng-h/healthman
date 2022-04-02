package com.rufeng.healthman.common;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.exceptions.PtException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author rufeng
 * @time 2022-02-28 14:48
 * @package com.rufeng.healthman.common
 * @description 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 未知异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> unknownError(Exception e) {
        e.printStackTrace();
        return ApiResponse.unknownError();
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ApiResponse<Void> duplicateError() {
        return ApiResponse.clientError("存在重复记录！请检查文件！");
    }

    @ExceptionHandler(PtException.class)
    public ApiResponse<Void> ptError(PtException e) {
        e.printStackTrace();
        return ApiResponse.clientError(e.getMessage());
    }

    /**
     * 认证异常
     *
     * @param e exp
     */
    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse<Void> authenticationError(AuthenticationException e) {
        return ApiResponse.authenticateFailed(e.getMessage());
    }

    /**
     * 请求参数、请求体异常
     */
    @ExceptionHandler({BindException.class})
    public ApiResponse<Void> validateFailed() {
        return ApiResponse.validateFailed();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<Void> accessDenied() {
        return ApiResponse.accessDenied();
    }
}
