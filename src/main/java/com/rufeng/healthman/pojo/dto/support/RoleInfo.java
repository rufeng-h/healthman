package com.rufeng.healthman.pojo.dto.support;

import com.rufeng.healthman.pojo.ptdo.PtRole;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author rufeng
 * @time 2022-03-28 9:46
 * @package com.rufeng.healthman.pojo.DTO.support
 * @description TODO
 */
@Data
public class RoleInfo implements Serializable {
    private String roleName;
    private LocalDateTime roleCreated;
    private Long roleId;
    /**
     * 资源Id
     */
    private String target;
    /**
     * 资源名称
     */
    private String targetName;

    public RoleInfo(PtRole role, String target, String targetName) {
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName();
        this.roleCreated = role.getRoleCreated();
        this.target = target;
        this.targetName = targetName;
    }
}
