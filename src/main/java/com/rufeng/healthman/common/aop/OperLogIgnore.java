package com.rufeng.healthman.common.aop;

import java.lang.annotation.*;

/**
 * @author rufeng
 * @time 2022-04-19 12:55
 * @package com.rufeng.healthman.common.aop
 * @description TODO
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLogIgnore {
}
