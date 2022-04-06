package com.rufeng.healthman.pojo.DTO.ptmeasurement;

import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.StuMeasurementStatus;
import com.rufeng.healthman.pojo.DTO.ptstu.StudentInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-04 12:11
 * @package com.rufeng.healthman.pojo.DTO.ptstu
 * @description 学生详情
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class StuMeasurementInfo extends StudentInfo {
    /**
     * 需要参加的体测
     */
    List<StuMeasurementStatus> msStatus;

    public StuMeasurementInfo(PtStudent student, String clsName, String clgCode, String clgName, List<StuMeasurementStatus> measurements) {
        super(student, clsName, clgCode, clgName);
        this.msStatus = measurements;
    }

    public StuMeasurementInfo(PtStudent student, String clsName, List<StuMeasurementStatus> measurements) {
        super(student, clsName);
        this.msStatus = measurements;
    }
}
