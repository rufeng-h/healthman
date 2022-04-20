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
    * 资源表
    */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtUrlResource implements Serializable {
    private Long resId;

    private String resName;

    private String pattern;

    private LocalDateTime created;

    private Long parentId;

    private String method;

    private static final long serialVersionUID = 1L;
}