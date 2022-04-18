package com.rufeng.healthman.pojo.data;

import com.rufeng.healthman.enums.UserTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author rufeng
 * @time 2022-04-19 1:40
 * @package com.rufeng.healthman.pojo.data
 * @description TODO
 */
@Data
@NoArgsConstructor
public class UpdatePwdFormdata {
    @Size(min = 6, max = 20)
    @NotNull
    private String oldPwd;
    @NotNull
    @Size(min = 6, max = 20)
    private String newPwd;
    @NotNull
    private UserTypeEnum userType;
    @NotNull
    @NotBlank
    private String userId;
}
