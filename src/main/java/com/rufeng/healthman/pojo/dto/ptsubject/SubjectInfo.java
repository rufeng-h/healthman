package com.rufeng.healthman.pojo.dto.ptsubject;

import com.rufeng.healthman.pojo.dto.ptscoresheet.SheetInfo;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-09 15:40
 * @package com.rufeng.healthman.pojo.DTO.ptsubject
 * @description TODO
 */
@Data
public class SubjectInfo {
    private String subName;
    private String subDesp;
    private Date subCreated;
    private Long subId;
    private Long compId;
    private String compName;
    private List<SheetInfo> sheetInfos;

    public SubjectInfo(PtSubject subject, String compName, List<SheetInfo> sheetInfos) {
        this.subId = subject.getSubId();
        this.subName = subject.getSubName();
        this.subCreated = subject.getSubCreated();
        this.subDesp = subject.getSubDesp();
        this.compId = subject.getCompId();
        this.compName = compName;
        this.sheetInfos = sheetInfos;
    }
}
