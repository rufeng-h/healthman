package com.rufeng.healthman.pojo.DTO.ptstu;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author rufeng
 * @time 2022-03-23 14:55
 * @package com.rufeng.healthman.pojo.DTO.ptstu
 * @description TODO
 */
@Data
public class StudentInfo {
    private Long sid;
    private String stuId;
    private String stuName;
    private LocalDate stuBirth;
    private GenderEnum stuGender;
    private String clsCode;
    private String clsName;
    private LocalDateTime stuCreated;
    private LocalDateTime stuLastLogin;
    private String avatar;
    private String stuDesp;
}
