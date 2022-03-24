package com.rufeng.healthman.pojo.DTO.support;

import lombok.Data;

/**
 * @author rufeng
 * @time 2022-03-09 18:24
 * @package com.rufeng.healthman.pojo.BO
 * @description 登录结果
 */
@Data
public class LoginResult {
    private String token;
    private UserInfo userInfo;

    public LoginResult(String token, UserInfo userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }
}
