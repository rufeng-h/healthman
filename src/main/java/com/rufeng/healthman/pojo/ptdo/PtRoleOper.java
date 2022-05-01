package com.rufeng.healthman.pojo.ptdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


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
    private String operId;
    private LocalDateTime created;
    private LocalDateTime modified;
}