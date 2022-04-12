package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 单项评分标准（国网）
 * @author rufeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtScoreSheet implements Serializable {
    /**
    * 科目
    */
    private Long subId;

    /**
    * 性别
    */
    private GenderEnum gender;

    private Integer grade;

    /**
    * 上区间
    */
    private BigDecimal upper;

    /**
    * 下区间
    */
    private BigDecimal lower;

    private Integer score;

    private Date createdTime;

    private Date lastModifyTime;

    /**
    * 级别
    */
    private String level;

    private Long id;

    private static final long serialVersionUID = 1L;
}