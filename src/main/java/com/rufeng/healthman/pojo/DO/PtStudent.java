package com.rufeng.healthman.pojo.DO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学生表
 *
 * @author rufeng
 * @TableName pt_student
 */
@Data
@Alias("PtStudent")
public class PtStudent implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String phone;
    /**
     * 学号
     */
    private Long number;
    /**
     * id
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 出生日期
     */
    private LocalDate birthday;
    /**
     * 性别
     */
    private GenderEnum gender;
    /**
     * 登陆密码
     */
    @JsonIgnore
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 学院id
     */
    private Long collegeId;
    /**
     * 专业代码
     */
    private String majorCode;
    /**
     * 班级代码
     */
    private String classCode;

    /**
     * desp
     */
    private String desp;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 上次修改时间
     */
    private LocalDateTime lastModifyTime;
    /**
     * 上次登录
     */
    private LocalDateTime lastLoginTime;
    /**
     *
     */
    @JsonIgnore
    private String deleted;
}