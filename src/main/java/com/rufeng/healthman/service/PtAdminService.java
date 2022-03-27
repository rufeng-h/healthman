package com.rufeng.healthman.service;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.pojo.DO.PtAdmin;
import com.rufeng.healthman.pojo.data.PtAdminFormdata;
import com.rufeng.healthman.pojo.DTO.ptadmin.AdminInfo;
import com.rufeng.healthman.pojo.DTO.support.UserInfo;
import com.rufeng.healthman.pojo.DTO.support.LoginResult;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.pojo.Query.PtAdminQuery;

/**
 * @author rufeng
 * @time 2022-03-09 22:58
 * @package com.rufeng.healthman.service
 * @description .
 */
public interface PtAdminService {
    /**
     * 主键查
     *
     * @param adminId id
     * @return user or null
     */
    PtAdmin getAdmin(String adminId);


    /**
     * 根据主键更新
     *
     * @param user user
     */
    void updateAdminByKey(PtAdmin user);

    /**
     * 分页查询
     *
     * @param page     页码
     * @param pageSize 每页条数
     * @param query    查询
     * @return page
     */
    ApiPage<AdminInfo> pageAdminInfo(Integer page, Integer pageSize, PtAdminQuery query);

    /**
     * 用户(管理员)添加
     *
     * @param data 用户信息
     * @return userinfo
     */
    UserInfo addAdmin(PtAdminFormdata data);

    /**
     * 登录
     *
     * @param loginQuery query
     * @return resutl
     */
    LoginResult login(LoginQuery loginQuery);

    /**
     * 当前登录管理员信息
     *
     * @return userinfo
     */
    AdminInfo adminInfo();
}
