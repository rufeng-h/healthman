package com.rufeng.healthman.domain;


import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * @author rufeng
 */
@Data
@Alias("PtClass")
public class PtClass {
    private Long id;
    private String code;
    private String name;
    private Long collegeId;
    private String majorCode;
    private String collegeName;
    private String majorName;
    private Long grade;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifyTime;
}
