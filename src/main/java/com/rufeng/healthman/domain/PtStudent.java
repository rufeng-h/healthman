package com.rufeng.healthman.domain;


import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author rufeng
 */
@Data
@Alias("PtStudent")
public class PtStudent {
    private Long number;
    private String name;
    private LocalDate birthday;
    private String gender;
    private String password;
    private String avatar;
    private Long collegeId;
    private String majorCode;
    private String classCode;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifyTime;
    private LocalDateTime lastLoginTime;
}
