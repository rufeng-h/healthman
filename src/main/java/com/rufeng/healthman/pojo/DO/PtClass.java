package com.rufeng.healthman.pojo.DO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 班级表
 *
 * @author rufeng
 * @TableName pt_class
 */
@Data
@Alias("PtClass")
public class PtClass implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String code;
    /**
     *
     */
    private Long id;
    /**
     * 班级名称
     */
    private String name;
    /**
     * 学院id
     */
    private Long collegeId;
    /**
     * 专业代码
     */
    private String majorCode;
    /**
     * 学院名称
     */
    private String collegeName;
    /**
     * 专业名称
     */
    private String majorName;
    /**
     *
     */
    private Integer grade;
    /**
     *
     */
    private LocalDateTime createdTime;
    /**
     *
     */
    private LocalDateTime lastModifyTime;
    /**
     *
     */
    private String deleted;
}