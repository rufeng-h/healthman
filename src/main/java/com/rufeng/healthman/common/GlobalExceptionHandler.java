package com.rufeng.healthman.common;

import com.rufeng.healthman.exceptions.test.TestException;
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
    @ExceptionHandler(TestException.class)
    public ApiResponse<Void> testError(TestException exception) {
        return ApiResponse.serverError(exception.getMessage());
    }
}
