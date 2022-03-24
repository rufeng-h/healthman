package com.rufeng.healthman.pojo.DO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 班级表
 * </p>
 *
 * @author rufeng
 * @since 2022-03-19
 */
@Data
@Alias("PtClass")
public class PtClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long clsId;

    private String clsCode;

    /**
     * 班级名称
     */
    private String clsName;

    /**
     * 学院代码
     */
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
}
