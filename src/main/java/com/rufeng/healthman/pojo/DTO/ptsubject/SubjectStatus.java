package com.rufeng.healthman.pojo.DTO.ptsubject;

import com.rufeng.healthman.pojo.DO.PtSubject;
import lombok.Data;

import java.util.Date;

/**
 * @author rufeng
 * @time 2022-04-05 15:09
 * @package com.rufeng.healthman.pojo.DTO.ptsubject
 * @description 科目完成状态
 */
@Data
public class SubjectStatus {
    private String subName;
    private String subDesp;
    private Date subCreated;
    private Long subId;
    private Boolean status;

    public SubjectStatus(PtSubject subject, Boolean status){
        this.subId = subject.getSubId();
        this.subName = subject.getSubName();
        this.subCreated = subject.getSubCreated();
        this.subDesp = subject.getSubDesp();
        this.status = status;
    }
}
