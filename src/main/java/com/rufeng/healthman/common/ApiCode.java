package com.rufeng.healthman.common;

import java.io.Serializable;

/**
 * @author rufeng
 * @time 2022-01-10 19:05
 * @package com.rufeng.healthman.common
 * @description 操作响应码
 */
public enum ApiCode implements Serializable {
    /**
     * 错误码
     */
    SUCCESS(200L, "操作成功"),
    FAILED(500L, "服务器异常，请稍后重试"),
    VALIDATE_FAILED(400L, "参数检验失败"),
    FORBBIDEN(403L, "没有相关权限"),
    AUTHENTICATE_FAILED(401L, "认证失败"),
    REDIRECT(301L, "");
    private final Long code;
    private final String message;

    ApiCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
