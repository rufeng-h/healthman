package com.rufeng.healthman.pojo.Query;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rufeng
 * @time 2022-03-18 0:16
 * @package com.rufeng.healthman.pojo.Query
 * @description 查询学生成绩
 */
@Data
@NoArgsConstructor
public class PtScoreQuery {
    private Long msId;
    private String stuId;
    private Long subId;
}
