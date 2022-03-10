package com.rufeng.healthman.service;

import com.rufeng.healthman.pojo.BO.LoginResult;
import com.rufeng.healthman.pojo.DO.PtUser;
import com.rufeng.healthman.pojo.Query.LoginQuery;

/**
 * @author rufeng
 * @time 2022-03-09 22:58
 * @package com.rufeng.healthman.service
 * @description .
 */
public interface PtUserService {
    /**
     * 主键查
     *
     * @param id id
     * @return user or null
     */
    PtUser getUser(Long id);

    /**
     * 登录
     *
     * @param loginQuery 登陆参数
     * @return result
     */
    LoginResult login(LoginQuery loginQuery);


    /**
     * 根据主键更新
     *
     * @param user user
     * @return count
     */
    Integer updateUserByKey(PtUser user);
}
