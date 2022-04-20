package com.rufeng.healthman.pojo.dto.ptmeasurement;

import com.rufeng.healthman.pojo.dto.ptstu.PtStudentPageInfo;
import com.rufeng.healthman.pojo.ptdo.PtStudent;
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
public class PtStuMeasurementPageInfo extends PtStudentPageInfo {
    /**
     * 需要参加的体测
     */
    List<StuMeasurementStatus> msStatus;

    public PtStuMeasurementPageInfo(PtStudent student, String clsName, String clgCode, String clgName, List<StuMeasurementStatus> measurements) {
        super(student, clsName, clgCode, clgName);
        this.msStatus = measurements;
    }

    public PtStuMeasurementPageInfo(PtStudent student, String clsName, List<StuMeasurementStatus> measurements) {
        super(student, clsName);
        this.msStatus = measurements;
    }
}
