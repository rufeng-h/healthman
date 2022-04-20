package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rufeng
 * @time 2022-04-20 10:30
 * @package com.rufeng.healthman.pojo.ptdo
 * @description TODO
 */

/**
    * 学院、教师关联
    */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtClgRole implements Serializable {
    private Long id;

    private String roleName;

    private LocalDateTime created;

    private LocalDateTime modified;

    private static final long serialVersionUID = 1L;
}