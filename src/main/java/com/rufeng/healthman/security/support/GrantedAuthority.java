package com.rufeng.healthman.security.support;

import java.io.Serializable;

/**
 * @author rufeng
 * @time 2022-04-19 18:31
 * @package com.rufeng.healthman.security.support
 * @description TODO
 */
public interface GrantedAuthority extends Serializable {
    /**
     * 权限
     *
     * @return authority
     */
    String getAuthority();
}
