package com.rufeng.healthman.service;

import com.rufeng.healthman.pojo.DTO.support.LoginResult;
import com.rufeng.healthman.pojo.DTO.support.UserInfo;
import com.rufeng.healthman.pojo.Query.LoginQuery;

/**
 * @author rufeng
 * @time 2022-03-21 15:43
 * @package com.rufeng.healthman.service
 * @description TODO
 */
public interface CommonService {
    /**
     * 登出
     */
    void logout();

    /**
     * 当前用户信息
     *
     * @return userInfo
     */
    UserInfo userInfo();

    /**
     * 登录
     *
     * @param loginQuery 登陆参数
     * @return result
     */
    LoginResult login(LoginQuery loginQuery);
}
