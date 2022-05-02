package com.rufeng.healthman.pojo.dto.subgroup;

import com.rufeng.healthman.pojo.ptdo.PtSubgroup;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-29 18:42
 * @package com.rufeng.healthman.pojo.subgroup
 * @description 科目组信息
 */
@Data
@NoArgsConstructor
public class SubGroupInfo {
    private List<String> sharedTeaIds;
    private String grpName;
    private Long grpId;
    private LocalDateTime grpModified;
    private LocalDateTime grpCreated;
    private String grpDesp;
    private List<PtSubject> subjects;
    private String grpCreatedTeaName;
    private String grpCreatedTeaId;
    private LocalDateTime shareTime;

    public SubGroupInfo(PtSubgroup subgroup, String grpCreatedTeaName) {
        this.grpCreated = subgroup.getGrpCreated();
        this.grpCreatedTeaId = subgroup.getGrpCreatedTeaId();
        this.grpCreatedTeaName = grpCreatedTeaName;
        this.grpModified = subgroup.getGrpModified();
        this.grpId = subgroup.getGrpId();
        this.grpName = subgroup.getGrpName();
        this.grpDesp = subgroup.getGrpDesp();
    }
}
