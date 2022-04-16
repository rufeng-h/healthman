package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 单项评分标准
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtScoreSheet implements Serializable {
    public static final String MIN_LOWER_STR = "-9999.99";
    public static final String MAX_UPPER_STR = "9999.99";
    public static final BigDecimal MIN_LOWER = new BigDecimal(MIN_LOWER_STR);
    public static final BigDecimal MAX_UPPER = new BigDecimal(MAX_UPPER_STR);
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

    private LocalDateTime createdTime;

    private LocalDateTime lastModifyTime;

    /**
     * 级别
     */
    private String level;

    private Long id;

    private static final long serialVersionUID = 1L;
}