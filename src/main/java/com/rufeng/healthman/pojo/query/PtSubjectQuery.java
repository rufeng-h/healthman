package com.rufeng.healthman.pojo.query;

import com.rufeng.healthman.enums.GradeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * @author rufeng
 * @time 2022-03-29 8:39
 * @package com.rufeng.healthman.pojo.Query
 * @description TODO
 */
@Data
@NoArgsConstructor
public class PtSubjectQuery {
    @Size(min = 1)
    private String subName;
    private GradeEnum grade;
}
