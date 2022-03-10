package com.rufeng.healthman.pojo.Query;

import com.rufeng.healthman.enums.UserTypeEnum;
import lombok.Data;

import javax.validation.constraints.Min;
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
    @Min(1)
    @NotNull
    private Long userId;
    @NotEmpty
    private String password;
    @NotNull
    private UserTypeEnum userTypeEnum;
}
