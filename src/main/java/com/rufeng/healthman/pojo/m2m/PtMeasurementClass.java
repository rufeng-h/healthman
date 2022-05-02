package com.rufeng.healthman.pojo.m2m;

import com.rufeng.healthman.pojo.ptdo.PtClass;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-02 18:27
 * @package com.rufeng.healthman.pojo.BO
 * @description 解决分页或者全查询多对多映射问题
 */
@Data
public class PtMeasurementClass {
    private List<PtClass> classes;
    private Long msId;
}
