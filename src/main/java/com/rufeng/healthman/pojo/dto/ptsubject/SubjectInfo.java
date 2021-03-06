package com.rufeng.healthman.pojo.dto.ptsubject;

import com.rufeng.healthman.pojo.dto.ptscoresheet.SubStudent;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import lombok.Data;

import java.time.LocalDateTime;
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
    private LocalDateTime subCreated;
    private Long subId;
    private Long compId;
    private String compName;
    private List<SubStudent> subStudents;
    private Boolean hasScore;

    public SubjectInfo(PtSubject subject, String compName, List<SubStudent> subStudents, Boolean hasScore) {
        this.subId = subject.getSubId();
        this.subName = subject.getSubName();
        this.subCreated = subject.getSubCreated();
        this.subDesp = subject.getSubDesp();
        this.compId = subject.getCompId();
        this.compName = compName;
        this.subStudents = subStudents;
        this.hasScore = hasScore;
    }
}
