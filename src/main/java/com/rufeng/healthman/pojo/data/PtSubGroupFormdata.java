package com.rufeng.healthman.pojo.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-28 16:52
 * @package com.rufeng.healthman.pojo.data
 * @description TODO
 */
@Data
@NoArgsConstructor
public class PtSubGroupFormdata {
    @NotEmpty
    private String grpName;
    @NotEmpty
    private String grpDesp;
    @NotEmpty
    private List<Long> subIds;
}
