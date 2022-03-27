package com.rufeng.healthman.pojo.DO;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-03-27 20:23
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */

/**
    * 角色表
    */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtRole implements Serializable {
    private Long roleId;

    private String adminId;

    private String roleName;

    /**
    * delete select update insert
    */
    private String roleValue;

    private Date roleCreated;

    private Date roleModified;

    private static final long serialVersionUID = 1L;
}