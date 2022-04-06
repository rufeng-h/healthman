package com.rufeng.healthman.pojo.DTO.subgroup;

import com.rufeng.healthman.pojo.DO.PtSubgroup;
import com.rufeng.healthman.pojo.DO.PtSubject;
import lombok.Data;

import java.util.Date;
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
    private Date grpModified;
    private Date grpCreated;
    private String grpDesp;
    private List<PtSubject> subjects;
    private String grpCreatedAdminName;
    private String grpCreatedAdminId;

    public SubGroupInfo(PtSubgroup subgroup, String grpCreatedAdminName, List<PtSubject> subjects) {
        this.grpCreated = subgroup.getGrpCreated();
        this.grpCreatedAdminId = subgroup.getGrpCreatedAdmin();
        this.grpCreatedAdminName = grpCreatedAdminName;
        this.grpModified = subgroup.getGrpModified();
        this.grpId = subgroup.getGrpId();
        this.grpName = subgroup.getGrpName();
        this.grpDesp = subgroup.getGrpDesp();
        this.subjects = subjects;
    }
}
