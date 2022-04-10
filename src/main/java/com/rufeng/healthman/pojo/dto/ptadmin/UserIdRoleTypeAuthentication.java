package com.rufeng.healthman.pojo.dto.ptadmin;

import com.rufeng.healthman.enums.UserTypeEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author rufeng
 * @time 2022-03-21 15:33
 * @package com.rufeng.healthman.pojo.DTO.ptuser
 * @description TODO
 */
public class UserIdRoleTypeAuthentication implements Authentication, Serializable {
    private final String userId;
    private final String username;
    private final UserTypeEnum userType;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserIdRoleTypeAuthentication(String userId, String username, UserTypeEnum userType, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.authorities = authorities;
        this.username = username;
        this.userType = userType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return this.userType + ":" + this.userId;
    }

    @Override
    public Object getDetails() {
        return this.userType;
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return this.username;
    }
}
