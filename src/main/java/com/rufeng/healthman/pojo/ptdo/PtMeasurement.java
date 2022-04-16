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
 * 测量记录表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtMeasurement implements Serializable {
    private Long msId;

    private String msName;

    private String msDesp;

    private LocalDateTime msCreated;

    private LocalDateTime msModified;

    /**
     * 科目组id
     */
    private Long grpId;

    private String msCreatedAdmin;

    private static final long serialVersionUID = 1L;
}