package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 角色表
 *
 * @author rufeng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtRole implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long roleId;
    private String roleName;
    private LocalDateTime roleCreated;
    private LocalDateTime roleModified;
}