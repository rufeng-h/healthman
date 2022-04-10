package com.rufeng.healthman.pojo.dto.ptmeasurement;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rufeng
 * @time 2022-04-05 14:56
 * @package com.rufeng.healthman.pojo.DTO.ptmeasurement
 * @description 体测科目完成情况
 */
@Data
@NoArgsConstructor
public class MeasurementSubStatus {
    private Long msId;
    private Long subId;
    private Boolean status;
}
