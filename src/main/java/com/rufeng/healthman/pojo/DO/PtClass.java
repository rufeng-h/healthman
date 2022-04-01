package com.rufeng.healthman.pojo.DO;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-03-27 20:23
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */

/**
    * 班级表
 * @author rufeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtClass implements Serializable {
    private String clsCode;

    private Long clsId;

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

    private Date clsCreated;

    private Date clsModified;

    private static final long serialVersionUID = 1L;
}