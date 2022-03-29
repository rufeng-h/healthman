package com.rufeng.healthman.pojo.DTO.subgroup;

import com.rufeng.healthman.pojo.DO.PtSubgroup;
import com.rufeng.healthman.pojo.DO.PtSubject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-29 18:42
 * @package com.rufeng.healthman.pojo.subgroup
 * @description TODO
 */
@Data
@NoArgsConstructor
public class SubGroupInfo {
    private String grpName;
    private Long grpId;
    private Date grpModified;
    private Date grpCreated;
    private String grpDesp;
    private List<PtSubject> subjects;
    private String createdAdminName;
    private String createdAdminId;

    public SubGroupInfo(PtSubgroup subgroup) {
        this.grpCreated = subgroup.getGrpCreated();
        this.createdAdminId = subgroup.getGrpCreatedAdmin();
        this.grpModified = subgroup.getGrpModified();
        this.grpId = subgroup.getGrpId();
        this.grpName = subgroup.getGrpName();
        this.grpDesp = subgroup.getGrpDesp();
    }
}
