package com.rufeng.healthman.pojo.query;

import lombok.Data;

/**
 * @author rufeng
 * @time 2022-03-09 22:12
 * @package com.rufeng.healthman.pojo.Query
 * @description .
 */
@Data
public class PtClassQuery {
    private String clsCode;
    private String clsName;
    private String clgCode;
    private Integer grade;
}
