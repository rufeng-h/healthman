package com.rufeng.healthman.pojo.DO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表
 *
 * @author rufeng
 * @TableName pt_role
 */
@Data
public class PtRole implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @JsonIgnore
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
    @JsonIgnore
    private String deleted;
    /**
     *
     */
    private Long userId;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return this.value;
    }
}