package com.rufeng.healthman.pojo.ptdo;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 管理员
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtAdmin implements Serializable {
    private static final long serialVersionUID = 1L;
    private String adminId;
    private Long aid;
    private String adminName;
    private LocalDateTime adminCreated;
    private LocalDateTime adminModified;
    private String password;
    private String phone;
    private String email;
    private String avatar;
    private LocalDateTime adminLastLogin;
    private String adminDesp;
    private GenderEnum adminGender;
}