package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-03-28 15:01
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */

/**
 * 用户表
 * @author rufeng
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

    /**
     * 所属学院
     */
    private String clgCode;

    private static final long serialVersionUID = 1L;
}