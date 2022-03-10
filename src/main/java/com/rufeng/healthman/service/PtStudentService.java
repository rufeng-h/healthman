package com.rufeng.healthman.service;

import com.rufeng.healthman.pojo.BO.LoginResult;
import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.Query.LoginQuery;

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
}
