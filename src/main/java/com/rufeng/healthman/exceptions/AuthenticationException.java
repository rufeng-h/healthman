package com.rufeng.healthman.exceptions;

/**
 * @author rufeng
 * @time 2022-04-19 17:52
 * @package com.rufeng.healthman.exceptions
 * @description TODO
 */
public class AuthenticationException extends PtException {
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(String message) {
        super(message, null);
    }
}
