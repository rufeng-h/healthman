package com.rufeng.healthman.pojo.query;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.groups.Default;

/**
 * @author rufeng
 * @time 2022-03-18 0:16
 * @package com.rufeng.healthman.pojo.Query
 * @description 查询学生成绩
 */
@Data
@NoArgsConstructor
public class PtScoreQuery {
    @NotNull(groups = MsQuery.class)
    private Long msId;
    @NotNull(groups = StuQuery.class)
    private String stuId;
    private Long subId;
    @Null(groups = StuQuery.class)
    private String stuName;

    public interface StuQuery extends Default {

    }

    public interface MsQuery extends Default {

    }
}
