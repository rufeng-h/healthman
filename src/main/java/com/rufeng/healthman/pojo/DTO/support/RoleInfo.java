package com.rufeng.healthman.pojo.DTO.support;

import com.rufeng.healthman.enums.RoleTypeEnum;
import com.rufeng.healthman.pojo.DO.PtRole;
import lombok.Data;

import java.util.Date;

/**
 * @author rufeng
 * @time 2022-03-28 9:46
 * @package com.rufeng.healthman.pojo.DTO.support
 * @description TODO
 */
@Data
public class RoleInfo {
    private String roleName;
    private byte roleValue;
    private Date roleCreated;
    private long roleId;
    private String target;
    private RoleTypeEnum roleType;

    public RoleInfo(PtRole role, String roleName) {
        this.roleName = roleName;
        this.roleCreated = role.getRoleCreated();
        this.roleId = role.getRoleId();
        this.roleType = role.getRoleType();
        this.target = role.getTarget();
        this.roleValue = role.getRoleValue();
    }

    public RoleInfo(String roleName, RoleTypeEnum roleType) {
        this.roleName = roleName;
        this.roleType = roleType;
    }
}
