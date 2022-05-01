package com.rufeng.healthman.pojo.data;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author rufeng
 * @time 2022-04-19 1:40
 * @package com.rufeng.healthman.pojo.data
 * @description TODO
 */
@Data
public class PtPwdUpdateFormdata {
    @Size(min = 6, max = 20)
    @NotNull
    private String oldPwd;
    @NotNull
    @Size(min = 6, max = 20)
    private String newPwd;
}
