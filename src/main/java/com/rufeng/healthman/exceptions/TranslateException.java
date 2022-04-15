package com.rufeng.healthman.exceptions;

import com.rufeng.healthman.exceptions.PtException;

/**
 * @author rufeng
 * @time 2022-04-15 22:30
 * @package com.rufeng.healthman.common.translation
 * @description TODO
 */
public class TranslateException extends PtException {
    public TranslateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TranslateException(String message) {
        this(message, null);
    }
}
