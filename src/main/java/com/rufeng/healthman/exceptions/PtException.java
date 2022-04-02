package com.rufeng.healthman.exceptions;

/**
 * @author rufeng
 * @time 2022-04-01 16:30
 * @package com.rufeng.healthman.exceptions
 * @description 项目自定义错误，可返回到前端
 */
public abstract class PtException extends RuntimeException {
    public PtException(String message, Throwable cause) {
        super(message, cause);
    }
}
