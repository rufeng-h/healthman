package com.rufeng.healthman.pojo.DO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author rufeng
 * @since 2022-03-19
 */
@Data
@Alias("PtAdmin")
public class PtAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String adminName;

    private String password;

    /**
     * 头像
     */
    private String avatar;

    private String email;

    private String adminDesp;

    private LocalDateTime adminCreated;

    private LocalDateTime adminModified;

    private LocalDateTime adminLastLogin;

    private String phone;

    private String adminId;

    private Long aid;
}
