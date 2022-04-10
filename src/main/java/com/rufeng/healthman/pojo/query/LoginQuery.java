package com.rufeng.healthman.pojo.query;

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
public class LoginQuery {
    @NotEmpty
    private String userId;
    @NotEmpty
    private String password;
    @NotNull
    private UserTypeEnum userType;
}
