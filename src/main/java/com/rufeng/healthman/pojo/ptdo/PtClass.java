package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-04-20 14:16
 * @package com.rufeng.healthman.pojo.ptdo
 * @description TODO
 */

/**
 * 班级表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtClass implements Serializable {
    private String clsCode;

    private Long clsId;

    private String clsName;

    private String clgCode;

    /**
     * 录入系统时年级
     */
    private Integer clsEntryGrade;

    /**
     * 录入系统年份
     */
    private Integer clsEntryYear;

    private LocalDateTime clsCreated;

    private LocalDateTime clsModified;

    private String teaId;

    private static final long serialVersionUID = 1L;
}