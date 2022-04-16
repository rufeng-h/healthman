package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-04-16 14:17
 * @package com.rufeng.healthman.pojo.ptdo
 * @description TODO
 */

/**
 * 用户表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    private LocalDateTime adminCreated;

    private LocalDateTime adminModified;

    private LocalDateTime adminLastLogin;

    private String phone;

    private Long aid;

    /**
     * 所属学院
     */
    private String clgCode;

    private static final long serialVersionUID = 1L;
}