package com.rufeng.healthman.pojo.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-29 23:57
 * @package com.rufeng.healthman.pojo.data
 * @description TODO
 */
@Data
@NoArgsConstructor
public class PtMesurementFormdata {
    @NotEmpty
    private String msName;
    @NotEmpty
    private String msDesp;
    private Long grpId;
    @NotEmpty
    private List<String> clsCodes;
}
