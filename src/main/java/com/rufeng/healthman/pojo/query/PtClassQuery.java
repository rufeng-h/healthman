package com.rufeng.healthman.pojo.query;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;


/**
 * @author rufeng
 * @time 2022-03-09 22:12
 * @package com.rufeng.healthman.pojo.Query
 * @description .
 */
@Data
public class PtClassQuery {
    @Length(min = 1)
    private String clsCode;
    @Length(min = 1)
    private String clsName;
    @Length(min = 1)
    private String clgCode;
    @Range(min = 1, max = 16)
    private Integer grade;
}
