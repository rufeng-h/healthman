package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.util.Date;

import com.rufeng.healthman.enums.RoleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-03-28 0:33
 * @package com.rufeng.healthman.pojo.DO
 * @description TODO
 */

/**
 * 角色表
 * @author rufeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PtRole implements Serializable {
    private Long roleId;

    private String adminId;

    /**
     * delete select update insert
     * 四位二进制依次表示
     */
    private Byte roleValue;

    private Date roleCreated;

    private Date roleModified;

    /**
     * 角色类型 SYSTEM COLLEGE CLASS
     */
    private RoleTypeEnum roleType;

    /**
     * 班级或学院id
     */
    private String target;

    private static final long serialVersionUID = 1L;
}