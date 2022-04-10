package com.rufeng.healthman.pojo.dto.ptmeasurement;

import com.rufeng.healthman.pojo.ptdo.PtMeasurement;
import lombok.Data;

/**
 * @author rufeng
 * @time 2022-04-05 1:27
 * @package com.rufeng.healthman.pojo.DTO.ptmeasurement
 * @description TODO
 */
@Data
public class StuMeasurementStatus {
    private Long msId;
    private String msName;
    private Boolean status;

    public StuMeasurementStatus(PtMeasurement measurement, Boolean status) {
        this.msId = measurement.getMsId();
        this.msName = measurement.getMsName();
        this.status = status;
    }
}
