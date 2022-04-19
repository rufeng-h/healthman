package com.rufeng.healthman.security.authentication;

import com.rufeng.healthman.security.support.UserInfo;
import org.springframework.lang.NonNull;

/**
 * @author rufeng
 * @time 2022-04-19 17:47
 * @package com.rufeng.healthman.security.authentication
 * @description TODO
 */
public class AuthenticationImpl implements Authentication {
    private final UserInfo userInfo;

    public AuthenticationImpl(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    @NonNull
    public UserInfo getUserInfo() {
        return userInfo;
    }
}
