package com.rufeng.healthman.pojo.DO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author rufeng
 * @since 2022-03-19
 */
@Data
@Alias("PtRole")
public class PtRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String adminId;

    private String roleName;

    /**
     * delete select update insert
     */
    private String roleValue;

    private LocalDateTime roleCreated;

    private LocalDateTime roleModified;

    private Long roleId;
}
