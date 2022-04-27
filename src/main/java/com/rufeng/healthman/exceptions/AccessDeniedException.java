package com.rufeng.healthman.exceptions;

/**
 * @author rufeng
 * @time 2022-04-19 18:29
 * @package com.rufeng.healthman.exceptions
 * @description TODO
 */
public class AccessDeniedException extends PtException {

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(String message) {
        this(message, null);
    }
}
