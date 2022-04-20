package com.rufeng.healthman.exceptions;

/**
 * @author rufeng
 * @time 2022-04-20 10:39
 * @package com.rufeng.healthman.exceptions
 * @description TODO
 */
public class UnknownException extends PtException {
    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownException(String message) {
        this(message, null);
    }
}
