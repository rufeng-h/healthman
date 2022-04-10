package com.rufeng.healthman.pojo.query;

import com.rufeng.healthman.enums.GenderEnum;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author rufeng
 * @time 2022-03-17 17:44
 * @package com.rufeng.healthman.pojo.Query
 * @description TODO
 */
@Data
public class PtScoreSheetQuery {
    @Min(1)
    @NotNull
    private Integer grade;
    @Min(1)
    @NotNull
    private Long subjectId;
    private GenderEnum gender;
}
