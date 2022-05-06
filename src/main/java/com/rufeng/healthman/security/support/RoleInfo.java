package com.rufeng.healthman.security.support;

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
 */
@Data
public class RoleInfo implements Serializable {
    private Long roleId;
    private String roleName;
    private LocalDateTime roleCreated;
    private LocalDateTime roleModified;
    private String roleDesp;
    private List<PtOperation> operations;
    /**
     * 状态
     */
    private Boolean status;

    public RoleInfo(PtRole role, List<PtOperation> operations) {
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName();
        this.roleCreated = role.getRoleCreated();
        this.roleModified = role.getRoleModified();
        this.roleDesp = role.getRoleDesp();
        this.status = role.getStatus();
        this.operations = operations;
    }
}
