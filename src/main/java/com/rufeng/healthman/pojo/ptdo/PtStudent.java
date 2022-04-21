package com.rufeng.healthman.pojo.ptdo;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 学生表
 *
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtStudent implements Serializable {
    private static final long serialVersionUID = 1L;
    private String stuId;
    /**
     * id
     */
    private Long sid;
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