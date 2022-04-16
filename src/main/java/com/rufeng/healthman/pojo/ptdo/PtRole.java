package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.rufeng.healthman.enums.RoleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-04-16 14:17
 * @package com.rufeng.healthman.pojo.ptdo
 * @description TODO
 */

/**
 * 角色表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtRole implements Serializable {
    private Long roleId;

    private String adminId;

    /**
     * delete select update insert
     * 四位二进制依次表示
     */
    private Byte roleValue;

    private LocalDateTime roleCreated;

    private LocalDateTime roleModified;

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