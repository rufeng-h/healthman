package com.rufeng.healthman.pojo.BO.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author rufeng
 * @time 2022-03-09 20:15
 * @package com.rufeng.healthman.pojo.BO.support
 * @description .
 */
@Data
class Role implements GrantedAuthority {
    private String roleName;
    private String value;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.value;
    }
}