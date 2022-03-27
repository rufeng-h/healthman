package com.rufeng.healthman.pojo.data;

import com.rufeng.healthman.enums.RoleTypeEnum;
import lombok.Data;

/**
 * @author rufeng
 * @time 2022-03-16 19:23
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */
@Data
public class PtAdminFormdata {
    private String username;
    private String password;
    private String email;
    private String phone;
    private RoleTypeEnum roleType;
    private String clgCode;
    private String classCode;
    private String desp;
}
