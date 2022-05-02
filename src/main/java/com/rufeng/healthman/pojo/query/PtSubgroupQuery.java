package com.rufeng.healthman.pojo.query;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author rufeng
 * @time 2022-03-29 18:46
 * @package com.rufeng.healthman.pojo.Query
 * @description TODO
 */
@Data
public class PtSubgroupQuery {
    @Size(min = 1)
    private String grpName;
    private String teaId;
    @NotNull
    private Boolean self;
}
