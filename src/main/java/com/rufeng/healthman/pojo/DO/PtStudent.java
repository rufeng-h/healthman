package com.rufeng.healthman.pojo.DO;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author rufeng
 * @since 2022-03-19
 */
@Data
@Alias("PtStudent")
public class PtStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long sid;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 姓名
     */
    private String stuName;

    /**
     * 出生日期
     */
    private LocalDate stuBirth;

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
    private LocalDateTime stuCreated;

    /**
     * 上次修改时间
     */
    private LocalDateTime stuModified;

    /**
     * 上次登录
     */
    private LocalDateTime stuLastLogin;

    private String stuDesp;
}
