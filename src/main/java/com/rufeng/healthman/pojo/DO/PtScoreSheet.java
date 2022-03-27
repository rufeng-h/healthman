package com.rufeng.healthman.pojo.DO;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 单项评分标准（国网）
 * </p>
 *
 * @author rufeng
 * @since 2022-03-19
 */
@Data
@Alias("PtScoreSheet")
public class PtScoreSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 科目
     */
    private Long subjectId;

    private Integer grade;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 上区间
     */
    @NotNull
    private BigDecimal upper;

    /**
     * 下区间
     */
    @NotNull
    private BigDecimal lower;

    @NotNull
    private Integer score;

    private LocalDateTime createdTime;

    private LocalDateTime lastModifyTime;

    /**
     * 级别
     */
    @NotNull
    private String level;

    private Long id;
}
