package com.rufeng.healthman.pojo.dto.ptmeasurement;

import com.rufeng.healthman.pojo.dto.ptscore.PtScoreInfo;
import com.rufeng.healthman.pojo.dto.ptsubject.SubjectStatus;
import com.rufeng.healthman.pojo.ptdo.PtMeasurement;
import lombok.Data;

import java.time.LocalDateTime;
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
    private List<SubjectStatus> subjects;
    private Long msId;
    private String msName;
    private String msCreatedAdminId;
    private String msCreatedAdminName;
    private LocalDateTime msCreated;
    private String msDesp;
    private List<PtScoreInfo> scores;

    public StuMeasurementDetail(PtMeasurement measurement, String msCreatedAdminName, List<SubjectStatus> subjects, List<PtScoreInfo> scores) {
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
