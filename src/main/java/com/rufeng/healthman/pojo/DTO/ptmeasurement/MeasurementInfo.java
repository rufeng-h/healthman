package com.rufeng.healthman.pojo.DTO.ptmeasurement;

import com.rufeng.healthman.pojo.DO.PtClass;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-30 12:07
 * @package com.rufeng.healthman.pojo.DTO.ptmeasurement
 * @description TODO
 */
@Data
@NoArgsConstructor
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
    private List<PtClass> classes;
    /**
     * 科目数
     */
    private Integer subCnt;
    /**
     * 已完成的学生数
     */
    private Integer compStuCnt;
    /**
     * 参与的班级数
     */
    private Integer clsCnt;

    /**
     * 参与的学生数
     */
    private Integer stuCnt;
}
