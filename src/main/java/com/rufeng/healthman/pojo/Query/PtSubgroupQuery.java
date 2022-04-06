package com.rufeng.healthman.pojo.Query;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * @author rufeng
 * @time 2022-03-29 18:46
 * @package com.rufeng.healthman.pojo.Query
 * @description TODO
 */
@Data
@NoArgsConstructor
public class PtSubgroupQuery {
    @Size(min = 1)
    private String grpName;
}
