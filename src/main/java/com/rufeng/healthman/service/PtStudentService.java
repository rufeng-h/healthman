package com.rufeng.healthman.service;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentInfo;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentUserInfo;
import com.rufeng.healthman.pojo.DTO.support.LoginResult;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.pojo.Query.PtStudentQuery;
import com.rufeng.healthman.pojo.file.PtStudentExcel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

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
    PtStudent getStudent(String number);

    /**
     * 学生登录
     *
     * @param loginQuery 登陆参数
     * @return login result
     */
    LoginResult login(LoginQuery loginQuery);

    /**
     * 按条件查询
     *
     * @param page     页码
     * @param pageSize 每页条数
     * @param query    查询条件
     * @return page
     */
    ApiPage<StudentInfo> pageStudentInfo(Integer page, Integer pageSize, PtStudentQuery query);

    /**
     * excel模板文件
     *
     * @return excel文件流
     */
    Resource fileTemplate();

    /**
     * ...
     *
     * @param file file
     * @return 添加条数
     */
    Integer uploadStudent(MultipartFile file);

    /**
     * 添加学生
     *
     * @param cachedDataList list
     * @return count
     */
    Integer addStudent(List<PtStudentExcel> cachedDataList);

    /**
     * 学生信息
     *
     * @return 登陆学生信息
     */
    StudentUserInfo studentInfo();
}
