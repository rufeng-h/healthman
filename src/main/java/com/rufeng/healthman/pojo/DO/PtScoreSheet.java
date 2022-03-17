package com.rufeng.healthman.pojo.DO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 单项评分标准（国网）
 *
 * @author rufeng
 * @TableName pt_score_sheet
 */
@Data
public class PtScoreSheet implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 科目
     */
    private Long subjectId;
    /**
     * 性别
     */
    private GenderEnum gender;
    /**
     * 年级
     */
    private Integer grade;
    /**
     * 上区间
     */
    private BigDecimal upper;
    /**
     * 下区间
     */
    private BigDecimal lower;
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
    @JsonIgnore
    private String deleted;
    /**
     * 级别
     */
    private String level;
    /**
     *
     */
    private Long id;
}