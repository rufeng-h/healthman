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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtRoleResource implements Serializable {
    private Long id;

    private Long roleId;

    private Long resId;

    private LocalDateTime created;

    private LocalDateTime modified;

    private static final long serialVersionUID = 1L;
}