package com.rufeng.healthman.security.authority;

import java.lang.annotation.*;

/**
 * @author rufeng
 * @time 2022-04-21 16:24
 * @package com.rufeng.healthman.security.annotation
 * @description TODO
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ApiAuthority {
    String value() default "";
}
