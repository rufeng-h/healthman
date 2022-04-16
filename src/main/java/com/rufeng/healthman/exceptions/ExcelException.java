package com.rufeng.healthman.exceptions;

/**
 * @author rufeng
 * @time 2022-03-25 0:23
 * @package com.rufeng
 * @description excel解析异常
 */
public class ExcelException extends PtException {
    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException(String message){
        this(message, null);
    }
}
