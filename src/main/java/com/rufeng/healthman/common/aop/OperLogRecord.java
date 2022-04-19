package com.rufeng.healthman.common.aop;

import com.rufeng.healthman.enums.OperTypeEnum;

import java.lang.annotation.*;

/**
 * @author rufeng
 * @time 2022-04-19 13:38
 * @package com.rufeng.healthman.common.aop
 * @description TODO
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLogRecord {
    String description();

    OperTypeEnum operType();
}
