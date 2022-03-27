package com.rufeng.healthman.pojo.DO;

import java.io.Serializable;
import java.util.Date;

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
    * 用户表
    */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtAdmin implements Serializable {
    private String adminId;

    private String adminName;

    private String password;

    /**
    * 头像
    */
    private String avatar;

    private String email;

    private String adminDesp;

    private Date adminCreated;

    private Date adminModified;

    private Date adminLastLogin;

    private String phone;

    private Long aid;

    private static final long serialVersionUID = 1L;
}