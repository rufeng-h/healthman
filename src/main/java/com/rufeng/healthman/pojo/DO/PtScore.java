package com.rufeng.healthman.pojo.DO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成绩表
 *
 * @author rufeng
 * @TableName pt_score
 */
@Data
@Alias("PtScore")
public class PtScore implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 学生学号
     */
    private Long stuNo;
    /**
     * 科目
     */
    private Long subjectId;
    /**
     * 年度
     */
    private Object year;
    /**
     *
     */
    private Long id;
    /**
     * 测试数据
     */
    private BigDecimal data;
    /**
     *
     */
    private Integer score;
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