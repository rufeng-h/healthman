package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.util.Date;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-03-27 20:23
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */

/**
    * 学生表
 * @author rufeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtStudent implements Serializable {
    /**
    * 学号
    */
    private String stuId;

    /**
    * id
    */
    private Long sid;

    /**
    * 姓名
    */
    private String stuName;

    /**
    * 出生日期
    */
    private Date stuBirth;

    /**
    * 性别
    */
    private GenderEnum stuGender;

    /**
    * 登陆密码
    */
    private String password;

    /**
    * 头像
    */
    private String avatar;

    /**
    * 班级代码
    */
    private String clsCode;

    /**
    * 创建时间
    */
    private Date stuCreated;

    /**
    * 上次修改时间
    */
    private Date stuModified;

    /**
    * 上次登录
    */
    private Date stuLastLogin;

    private String stuDesp;

    private static final long serialVersionUID = 1L;
}