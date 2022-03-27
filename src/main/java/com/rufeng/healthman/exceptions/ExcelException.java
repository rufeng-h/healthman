package com.rufeng.healthman.exceptions;

/**
 * @author rufeng
 * @time 2022-03-25 0:23
 * @package com.rufeng
 * @description TODO
 */
public abstract class ExcelException extends RuntimeException {
    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }
}
