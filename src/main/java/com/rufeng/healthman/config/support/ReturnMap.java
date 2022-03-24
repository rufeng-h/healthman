package com.rufeng.healthman.config.support;

import java.lang.annotation.*;

/**
 * @author rufeng
 * @time 2022-03-24 9:59
 * @package com.rufeng.healthman.annotation
 * @description 数据库查出两列，返回map，一列key，一列value
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ReturnMap {

}

