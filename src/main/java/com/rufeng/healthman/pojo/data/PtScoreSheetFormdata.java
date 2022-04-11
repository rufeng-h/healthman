package com.rufeng.healthman.pojo.data;

import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.enums.GradeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author rufeng
 * @time 2022-04-11 13:48
 * @package com.rufeng.healthman.pojo.data
 * @description TODO
 */
@NoArgsConstructor
@Data
public class PtScoreSheetFormdata {
    @NotNull
    private GenderEnum gender;
    @NotNull
    private GradeEnum gradeEnum;
    @NotNull
    @Min(1)
    private Long subjectId;
    private BigDecimal upper;
    private BigDecimal lower;
}
