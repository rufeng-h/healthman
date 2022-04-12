package com.rufeng.healthman.pojo.data;

import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;
import com.rufeng.healthman.validation.group.Insert;
import com.rufeng.healthman.validation.group.Update;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-27 16:40
 * @package com.rufeng.healthman.pojo.Query
 * @description 添加科目
 */
@Data
public class PtSubjectFormdata {
    @NotNull(groups = Update.class)
    @Null(groups = Insert.class)
    @Min(1)
    private Long subId;
    @NotEmpty
    private String subName;
    @NotEmpty
    private String subDesp;
    private Long compId;
    @NotEmpty
    @Valid
    private List<@NotNull SubStudent> subStudents;
}
