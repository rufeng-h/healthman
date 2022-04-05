package com.rufeng.healthman.pojo.DTO.ptmeasurement;

import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.DO.PtMeasurement;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-30 12:07
 * @package com.rufeng.healthman.pojo.DTO.ptmeasurement
 * @description TODO
 */
@Data
public class MeasurementInfo {
    private Long msId;
    private String msName;
    private Date msModified;
    private String msDesp;
    private Date msCreated;
    private String grpName;
    private Long grpId;
    private String msCreatedAdminId;
    private String msCreatedAdminName;
    /**
     * 科目数
     */
    private Integer subCnt;
    /**
     * 已完成的学生数
     */
    private Integer compStuCnt;
    /**
     * 参与的学生数
     */
    private Integer stuCnt;
    private List<PtClass> classes;

    public MeasurementInfo(PtMeasurement measurement) {
        this.msId = measurement.getMsId();
        this.msName = measurement.getMsName();
        this.msModified = measurement.getMsModified();
        this.msDesp = measurement.getMsDesp();
        this.msCreated = measurement.getMsCreated();
        this.msCreatedAdminId = measurement.getMsCreatedAdmin();
        this.grpId = measurement.getGrpId();
    }
}