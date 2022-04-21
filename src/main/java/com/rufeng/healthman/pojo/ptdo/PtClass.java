package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 班级表
 *
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtClass implements Serializable {
    private static final long serialVersionUID = 1L;
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
}