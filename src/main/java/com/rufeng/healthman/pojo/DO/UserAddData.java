package com.rufeng.healthman.pojo.DO;

import com.rufeng.healthman.enums.RoleTypeEnum;
import lombok.Data;

/**
 * @author rufeng
 * @time 2022-03-16 19:23
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */
@Data
public class UserAddData {
    private String username;
    private String password;
    private String email;
    private String phone;
    private RoleTypeEnum roleType;
    private Long collegeId;
    private String classCode;
    private String desp;
}
