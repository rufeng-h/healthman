package com.rufeng.healthman.common;

import com.rufeng.healthman.common.api.ApiResponse;
import org.springframework.security.core.AuthenticationException;
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
    public ApiResponse<Void> unknownError() {
        return ApiResponse.unknownError();
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
}
