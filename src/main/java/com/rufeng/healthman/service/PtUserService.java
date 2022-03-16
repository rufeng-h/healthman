package com.rufeng.healthman.service;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.pojo.DO.PtUser;
import com.rufeng.healthman.pojo.DO.UserAddData;
import com.rufeng.healthman.pojo.DTO.ptuser.LoginResult;
import com.rufeng.healthman.pojo.DTO.ptuser.UserInfo;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.pojo.Query.PtUserQuery;

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

    /**
     * 分页查询
     *
     * @param page     页码
     * @param pageSize 每页条数
     * @param query    查询
     * @return page
     */
    ApiPage<PtUser> pageUser(Integer page, Integer pageSize, PtUserQuery query);

    /**
     * 用户(管理员)添加
     *
     * @param data 用户信息
     * @return userinfo
     */
    UserInfo addUser(UserAddData data);
}
