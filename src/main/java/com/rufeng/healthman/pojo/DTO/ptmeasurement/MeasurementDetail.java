package com.rufeng.healthman.pojo.DTO.ptmeasurement;

import com.rufeng.healthman.pojo.DO.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-02 13:18
 * @package com.rufeng.healthman.pojo.DTO.ptmeasurement
 * @description 体测详情，管理员
 */
@Data
public class MeasurementDetail {
    private Long msId;
    private String msName;
    private Date msModified;
    private String msDesp;
    private Date msCreated;
    private String grpName;
    private Long grpId;
    private String msCreatedAdminId;
    private String msCreatedAdminName;
    private List<PtClass> classes;
    private List<PtSubject> subjects;
    private Integer stuCnt;
    private Integer compStuCnt;

    public MeasurementDetail(PtMeasurement measurement, PtAdmin admin, PtSubgroup subgroup,
                             List<PtSubject> subjects, List<PtClass> classes, int totalStuCnt, int compStuCnt) {
        this.classes = classes;
        this.msId = measurement.getMsId();
        this.msName = measurement.getMsName();
        this.msModified = measurement.getMsModified();
        this.msDesp = measurement.getMsDesp();
        this.grpName = subgroup.getGrpName();
        this.grpId = subgroup.getGrpId();
        this.msCreatedAdminId = admin.getAdminId();
        this.msCreatedAdminName = admin.getAdminName();
        this.msCreated = measurement.getMsCreated();
        this.subjects = subjects;
        this.stuCnt = totalStuCnt;
        this.compStuCnt = compStuCnt;
    }
}
