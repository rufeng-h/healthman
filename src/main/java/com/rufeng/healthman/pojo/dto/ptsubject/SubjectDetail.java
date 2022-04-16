package com.rufeng.healthman.pojo.dto.ptsubject;

import com.rufeng.healthman.pojo.ptdo.PtSubject;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-10 18:19
 * @package com.rufeng.healthman.pojo.dto.ptsubject
 * @description TODO
 */
@Data
public class SubjectDetail {
    private String subName;
    private String subDesp;
    private LocalDateTime subCreated;
    private Long subId;
    private Long compId;
    private String compName;
    private List<String> levels;

    public SubjectDetail(PtSubject subject, String compName, List<String> levels) {
        this.subId = subject.getSubId();
        this.subName = subject.getSubName();
        this.subCreated = subject.getSubCreated();
        this.subDesp = subject.getSubDesp();
        this.compId = subject.getCompId();
        this.compName = compName;
        this.levels = levels;
    }
}
