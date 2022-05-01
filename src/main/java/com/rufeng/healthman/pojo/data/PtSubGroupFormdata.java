package com.rufeng.healthman.pojo.data;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-28 16:52
 * @package com.rufeng.healthman.pojo.data
 * @description TODO
 */
@Data
public class PtSubGroupFormdata {
    @NotEmpty
    private String grpName;
    @NotEmpty
    private String grpDesp;
    @NotEmpty
    private List<Long> subIds;
}
