package com.rufeng.healthman.pojo.query;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * @author rufeng
 * @time 2022-03-30 16:40
 * @package com.rufeng.healthman.pojo.Query
 * @description TODO
 */
@Data
@NoArgsConstructor
public class PtMeasurementQuery {
    @Size(min = 1)
    private String msName;

    private String teaId;
}
