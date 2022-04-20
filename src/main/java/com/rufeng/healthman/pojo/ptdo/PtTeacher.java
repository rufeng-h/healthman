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
 * @author rufeng
 * @time 2022-04-20 13:54
 * @package com.rufeng.healthman.pojo.ptdo
 * @description TODO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtTeacher implements Serializable {
    private static final long serialVersionUID = 1L;
    private String teaId;
    private String teaName;
    private String password;
    /**
     * 头像
     */
    private String avatar;
    private String email;
    private String teaDesp;
    private LocalDateTime teaCreated;
    private LocalDateTime teaModified;
    private LocalDateTime teaLastLogin;
    private String phone;
    private Long tid;
    /**
     * 所属学院
     */
    private String clgCode;
    private GenderEnum teaGender;
    private LocalDate teaBirth;
    private Boolean principal;
}