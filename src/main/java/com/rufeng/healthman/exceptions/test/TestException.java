package com.rufeng.healthman.exceptions.test;

/**
 * @author rufeng
 * @time 2022-02-28 14:51
 * @package com.rufeng.healthman.exceptions.test
 * @description 测试异常
 */
public class TestException extends RuntimeException {
    public TestException(String message) {
        this(message, null);
    }

    public TestException(String message, Throwable cause) {
        super(message, cause);
    }
}
