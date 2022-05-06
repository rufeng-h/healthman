package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * 角色资源
 *
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtRoleOper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long roleId;
    private Long operId;
    private LocalDateTime created;
    private LocalDateTime modified;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PtRoleOper)) {
            return false;
        }
        PtRoleOper that = (PtRoleOper) o;
        return Objects.equals(roleId, that.roleId) && Objects.equals(operId, that.operId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, operId);
    }
}