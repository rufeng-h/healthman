package com.rufeng.healthman.pojo.DTO.ptstu;

import com.rufeng.healthman.pojo.DO.PtMeasurement;
import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.MeasurementStatus;
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
public class StuMsInfo extends StudentInfo {
    /**
     * 需要参加的体测
     */
    List<MeasurementStatus> msStatus;

    public StuMsInfo(PtStudent student, String clsName, String clgCode, String clgName, List<MeasurementStatus> measurements) {
        super(student, clsName, clgCode, clgName);
        this.msStatus = measurements;
    }

    public StuMsInfo(PtStudent student, String clsName, List<MeasurementStatus> measurements) {
        super(student, clsName);
        this.msStatus = measurements;
    }
}
