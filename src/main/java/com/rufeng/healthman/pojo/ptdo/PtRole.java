package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-04-19 22:58
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

    private String roleName;

    private LocalDateTime roleCreated;

    private LocalDateTime roleModified;

    private static final long serialVersionUID = 1L;
}