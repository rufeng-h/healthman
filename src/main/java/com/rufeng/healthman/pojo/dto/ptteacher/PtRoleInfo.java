package com.rufeng.healthman.pojo.dto.ptteacher;

import com.rufeng.healthman.pojo.ptdo.PtOperation;
import com.rufeng.healthman.pojo.ptdo.PtRole;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-28 9:46
 * @package com.rufeng.healthman.pojo.DTO.support
 * @description TODO
 */
@Data
public class PtRoleInfo implements Serializable {
    private Long roleId;
    private String roleName;
    private LocalDateTime roleCreated;
    private LocalDateTime roleModified;
    private String roleDesp;
    private List<PtOperation> authorities;
    /**
     * 状态
     */
    private Boolean status;

    public PtRoleInfo(PtRole role, List<PtOperation> authorities) {
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName();
        this.roleCreated = role.getRoleCreated();
        this.roleModified = role.getRoleModified();
        this.roleDesp = role.getRoleDesp();
        this.status = role.getStatus();
        this.authorities = authorities;
    }
}
