package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-04-20 9:31
 * @package com.rufeng.healthman.pojo.ptdo
 * @description TODO
 */

/**
 * 教师、角色
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtTeacherRole implements Serializable {
    private Long id;

    private String teaId;

    private Long roleId;

    private LocalDateTime created;

    private LocalDateTime modified;

    private static final long serialVersionUID = 1L;
}