package com.rufeng.healthman.pojo.DTO.ptmeasurement;

import com.rufeng.healthman.pojo.DO.PtMeasurement;
import com.rufeng.healthman.pojo.DTO.ptscore.ScoreInfo;
import com.rufeng.healthman.pojo.DTO.ptsubject.SubjectStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-05 14:21
 * @package com.rufeng.healthman.pojo.DTO.ptmeasurement
 * @description 体测详情，学生
 */
@Data
public class StuMeasurementDetail {
    /**
     * 需要测试的科目及状态
     */
    List<SubjectStatus> subjects;
    private Long msId;
    private String msName;
    private String msCreatedAdminId;
    private String msCreatedAdminName;
    private Date msCreated;
    private String msDesp;
    private List<ScoreInfo> scores;

    public StuMeasurementDetail(PtMeasurement measurement, String msCreatedAdminName, List<SubjectStatus> subjects, List<ScoreInfo> scores) {
        this.msId = measurement.getMsId();
        this.msName = measurement.getMsName();
        this.msCreatedAdminId = measurement.getMsCreatedAdmin();
        this.msDesp = measurement.getMsDesp();
        this.msCreatedAdminName = msCreatedAdminName;
        this.msCreated = measurement.getMsCreated();
        this.subjects = subjects;
        this.scores = scores;
    }
}
