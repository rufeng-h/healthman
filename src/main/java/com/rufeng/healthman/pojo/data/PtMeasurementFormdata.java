package com.rufeng.healthman.pojo.data;

import com.rufeng.healthman.validation.group.Insert;
import com.rufeng.healthman.validation.group.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-29 23:57
 * @package com.rufeng.healthman.pojo.data
 * @description TODO
 */
@Data
public class PtMeasurementFormdata {
    @NotNull(groups = Update.class)
    @Null(groups = Insert.class)
    private Long msId;
    @NotEmpty
    private String msName;
    @NotEmpty
    private String msDesp;
    @NotNull
    private Long grpId;
    @NotEmpty
    private List<String> clsCodes;
}
