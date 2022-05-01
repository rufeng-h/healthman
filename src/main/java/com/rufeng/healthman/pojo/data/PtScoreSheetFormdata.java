package com.rufeng.healthman.pojo.data;

import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.enums.GradeEnum;
import com.rufeng.healthman.validation.group.Insert;
import com.rufeng.healthman.validation.group.Update;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

import static com.rufeng.healthman.pojo.ptdo.PtScoreSheet.MAX_UPPER_STR;
import static com.rufeng.healthman.pojo.ptdo.PtScoreSheet.MIN_LOWER_STR;

/**
 * @author rufeng
 * @time 2022-04-11 13:48
 * @package com.rufeng.healthman.pojo.data
 * @description TODO
 */
@Data
public class PtScoreSheetFormdata {
    @Null(groups = Insert.class)
    @NotNull(groups = Update.class)
    private Long id;
    @NotNull
    private GenderEnum gender;
    @NotNull
    private GradeEnum grade;
    @NotNull
    @Min(1)
    private Long subId;
    @DecimalMax(MAX_UPPER_STR)
    @DecimalMin(MIN_LOWER_STR)
    private BigDecimal upper;
    @DecimalMax(MAX_UPPER_STR)
    @DecimalMin(MIN_LOWER_STR)
    private BigDecimal lower;
    @NotEmpty
    private String level;
    @Min(0)
    @NotNull
    private Integer score;
}
