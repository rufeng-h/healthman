package com.rufeng.healthman.pojo.ptdo;

import java.io.Serializable;
import java.time.LocalDateTime;
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
 * 运动能力
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtCompetency implements Serializable {
    private Long compId;

    private String compName;

    private String compDesp;

    private LocalDateTime compCreated;

    private LocalDateTime compMidified;

    private static final long serialVersionUID = 1L;
}