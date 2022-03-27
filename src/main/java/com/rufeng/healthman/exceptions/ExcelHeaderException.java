package com.rufeng.healthman.exceptions;

/**
 * @author rufeng
 * @time 2022-03-25 0:15
 * @package com.rufeng
 * @description TODO
 */

public class ExcelHeaderException extends ExcelException {
    public ExcelHeaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelHeaderException(String message) {
        this(message, null);
    }
}
