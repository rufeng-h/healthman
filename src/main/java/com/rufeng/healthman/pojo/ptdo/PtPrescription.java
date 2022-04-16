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
 * 运动处方
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PtPrescription implements Serializable {
    private Long prsId;

    private Long compId;

    private String prsLevel;

    private String prsText;

    private LocalDateTime prsCreated;

    private LocalDateTime prsModified;

    private static final long serialVersionUID = 1L;
}