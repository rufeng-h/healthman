package com.rufeng.healthman.pojo.DTO.ptuser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author rufeng
 * @time 2022-03-09 20:15
 * @package com.rufeng.healthman.pojo.BO.support
 * @description .
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    private String roleName;
    private String value;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.getValue();
    }
}