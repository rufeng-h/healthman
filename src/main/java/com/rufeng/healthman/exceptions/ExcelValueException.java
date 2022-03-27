package com.rufeng.healthman.exceptions;

/**
 * @author rufeng
 * @time 2022-03-27 23:52
 * @package com.rufeng.healthman.exceptions
 * @description TODO
 */
public class ExcelValueException extends ExcelException {
    public ExcelValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelValueException(String message) {
        this(message, null);
    }
}
