package com.rufeng.healthman.service;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.DTO.ptuser.LoginResult;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.pojo.Query.PtStudentQuery;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-09 22:09
 * @package com.rufeng.healthman.service
 * @description .
 */
public interface PtStudentService {
    /**
     * 主键查询
     *
     * @param number 学号
     * @return stu
     */
    PtStudent getStudent(Long number);

    /**
     * 学生登录
     *
     * @param loginQuery 登陆参数
     * @return login result
     */
    LoginResult login(LoginQuery loginQuery);

    /**
     * 按条件查询
     * @param page 页码
     * @param pageSize 每页条数
     * @param query 查询条件
     * @return page
     */
    ApiPage<PtStudent> pageStudent(Integer page, Integer pageSize, PtStudentQuery query);
}
