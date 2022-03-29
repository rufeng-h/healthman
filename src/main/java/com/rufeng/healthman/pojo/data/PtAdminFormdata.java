package com.rufeng.healthman.pojo.data;

import com.rufeng.healthman.enums.RoleTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-16 19:23
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */
@Data
public class PtAdminFormdata {
    @NotEmpty
    private String adminId;
    @NotEmpty
    private String adminName;
    @NotEmpty
    private String password;
    private String email;
    private String phone;
    @NotEmpty
    private List<RoleTypeEnum> roleTypes;
    private List<@NotEmpty String> clgCodes;
    private List<@NotEmpty String> clsCodes;
    private String desp;
}
