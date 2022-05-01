package com.rufeng.healthman.pojo.dto.ptteacher;

import com.rufeng.healthman.pojo.ptdo.PtTeacher;
import lombok.Data;

/**
 * @author rufeng
 * @time 2022-05-01 10:27
 * @package com.rufeng.healthman.pojo.dto.ptteacher
 * @description 用于前端Select组件
 */
@Data
public class PtTeacherListInfo {
    private String teaId;
    private String teaName;

    /**
     * 所属学院
     */
    private String clgName;

    public PtTeacherListInfo(PtTeacher teacher, String clgName) {
        this.teaId = teacher.getTeaId();
        this.teaName = teacher.getTeaName();
        this.clgName = clgName;
    }
}