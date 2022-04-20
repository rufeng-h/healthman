package com.rufeng.healthman.pojo.query;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author rufeng
 * @time 2022-03-15 12:26
 * @package com.rufeng.healthman.pojo.Query
 * @description TODO
 */
@Data
public class PtTeacherQuery {
    @Length(min = 1)
    private String clgCode;
    @Length(min = 1)
    private String teaName;
    @Length(min = 1)
    private String teaId;
}
