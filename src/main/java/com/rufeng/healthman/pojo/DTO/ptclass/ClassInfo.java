package com.rufeng.healthman.pojo.DTO.ptclass;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author rufeng
 * @time 2022-03-22 23:06
 * @package com.rufeng.healthman.pojo.DTO.ptclass
 * @description TODO
 */
@Data
public class ClassInfo {
    private String clsId;
    private String clsCode;
    private String clsName;
    private String clgCode;
    private String clgName;
    private Integer stuCount;
    private Integer clsEntryYear;
    private LocalDateTime clsCreated;
}
