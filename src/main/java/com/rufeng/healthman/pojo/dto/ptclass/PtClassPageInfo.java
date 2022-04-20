package com.rufeng.healthman.pojo.dto.ptclass;

import com.rufeng.healthman.pojo.ptdo.PtClass;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author rufeng
 * @time 2022-03-22 23:06
 * @package com.rufeng.healthman.pojo.DTO.ptclass
 * @description TODO
 */
@Data
public class PtClassPageInfo {
    private Long clsId;
    private String clsCode;
    private String clsName;
    private String clgCode;
    private String clgName;
    private Integer clsEntryYear;
    private Integer clsEntryGrade;
    private LocalDateTime clsCreated;
    private String teaName;
    private String teaId;
    private Integer stuCount;

    public PtClassPageInfo(PtClass ptClass, String clgName, String teaName, Integer stuCount) {
        this.clsId = ptClass.getClsId();
        this.clsCode = ptClass.getClsCode();
        this.clsCreated = ptClass.getClsCreated();
        this.clgCode = ptClass.getClgCode();
        this.clgName = clgName;
        this.clsName = ptClass.getClsName();
        this.clsEntryYear = ptClass.getClsEntryYear();
        this.clsEntryGrade = ptClass.getClsEntryGrade();
        this.teaName = teaName;
        this.teaId = ptClass.getTeaId();
        this.stuCount = stuCount;
    }
}
