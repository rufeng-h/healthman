package com.rufeng.healthman.pojo.DO;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表
 *
 * @author rufeng
 * @TableName pt_role
 */
@Data
public class PtRole implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String roleName;
    /**
     *
     */
    private String value;
    /**
     *
     */
    private LocalDateTime createdTime;
    /**
     *
     */
    private LocalDateTime lastModifyTime;
    /**
     *
     */
    private String deleted;
    /**
     *
     */
    private Long userId;
}