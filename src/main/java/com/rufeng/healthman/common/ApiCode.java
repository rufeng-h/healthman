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
    CLIENT_ERROR(400L, "请求异常"),
    VALIDATE_FAILED(401L, "参数检验失败"),
    FORBBIDEN(403L, "没有相关权限"),
    AUTHENTICATE_FAILED(402L, "认证失败"),
    SERVER_ERROR(500L, "服务器异常"),
    UNKNON_ERROR(600L, "未知异常");
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
