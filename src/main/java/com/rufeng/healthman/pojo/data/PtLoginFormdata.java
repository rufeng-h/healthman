package com.rufeng.healthman.pojo.data;

import com.rufeng.healthman.enums.UserTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author rufeng
 * @time 2022-03-09 18:19
 * @package com.rufeng.healthman.pojo.Query
 * @description 登陆参数
 */
@Data
public class PtLoginFormdata {
    @NotEmpty
    private String userId;
    @NotEmpty
    private String password;
    @NotNull
    private UserTypeEnum userType;
}
