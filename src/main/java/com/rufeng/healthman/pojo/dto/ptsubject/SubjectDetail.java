package com.rufeng.healthman.pojo.dto.ptsubject;

import com.rufeng.healthman.pojo.ptdo.PtSubject;
import com.rufeng.healthman.pojo.dto.ptscoresheet.SheetInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-28 18:29
 * @package com.rufeng.healthman.pojo.DTO.ptsubject
 * @description TODO
 */
@Data
@NoArgsConstructor
public class SubjectDetail {
    private String subName;
    private String subDesp;
    private Date subCreated;
    private Long subId;
    private List<SheetInfo> sheetInfos = new ArrayList<>();

    public SubjectDetail(PtSubject subject) {
        this.subCreated = subject.getSubCreated();
        this.subDesp = subject.getSubDesp();
        this.subName = subject.getSubName();
        this.subId = subject.getSubId();
    }
}
