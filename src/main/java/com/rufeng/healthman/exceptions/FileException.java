package com.rufeng.healthman.exceptions;

/**
 * @author rufeng
 * @time 2022-04-18 12:57
 * @package com.rufeng.healthman.exceptions
 * @description TODO
 */
public class FileException extends PtException {
    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileException(String message) {
        this(message, null);
    }
}
