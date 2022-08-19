package com.rufeng.healthman.common.aop;

import java.lang.annotation.*;

/**
 * @author rufeng
 * @time 2022-04-19 13:38
 * @package com.rufeng.healthman.common.aop
 * @description 操作日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLogRecord {
    String value() default "";

    String spel() default "";
}
