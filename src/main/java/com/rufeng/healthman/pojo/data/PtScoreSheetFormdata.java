package com.rufeng.healthman.pojo.data;

import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.enums.GradeEnum;
import com.rufeng.healthman.pojo.DO.PtScoreSheet;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * @author rufeng
 * @time 2022-03-27 16:40
 * @package com.rufeng.healthman.pojo.Query
 * @description TODO
 */
@Data
public class PtScoreSheetFormdata {
    @NotEmpty
    private String subName;
    private String subDesp;
    @NotEmpty
    @Valid
    private List<@NotNull PtScoreSheet> scoreSheet;
    @NotEmpty
    private List<GradeEnum> grades;
    private Set<GenderEnum> genders;
}
