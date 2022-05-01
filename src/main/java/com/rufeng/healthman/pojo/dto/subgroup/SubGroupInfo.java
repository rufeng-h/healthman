package com.rufeng.healthman.pojo.dto.subgroup;

import com.rufeng.healthman.pojo.ptdo.PtSubgroup;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-29 18:42
 * @package com.rufeng.healthman.pojo.subgroup
 * @description TODO
 */
@Data
public class SubGroupInfo {
    private String grpName;
    private Long grpId;
    private LocalDateTime grpModified;
    private LocalDateTime grpCreated;
    private String grpDesp;
    private List<PtSubject> subjects;
    private String grpCreatedAdminName;
    private String grpCreatedAdminId;

    public SubGroupInfo(PtSubgroup subgroup, String grpCreatedAdminName, List<PtSubject> subjects) {
        this.grpCreated = subgroup.getGrpCreated();
        this.grpCreatedAdminId = subgroup.getGrpCreatedTea();
        this.grpCreatedAdminName = grpCreatedAdminName;
        this.grpModified = subgroup.getGrpModified();
        this.grpId = subgroup.getGrpId();
        this.grpName = subgroup.getGrpName();
        this.grpDesp = subgroup.getGrpDesp();
        this.subjects = subjects;
    }
}
