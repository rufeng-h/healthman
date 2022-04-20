package com.rufeng.healthman.pojo.dto.ptcollege;

import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.pojo.ptdo.PtTeacher;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

/**
 * @author rufeng
 * @time 2022-04-20 16:09
 * @package com.rufeng.healthman.pojo.dto.ptcollege
 * @description TODO
 */
@Data
public class PtCollegePageInfo {
    private String clgCode;
    private Long clgId;
    /**
     * 学院名称
     */
    private String clgName;
    /**
     * 办公室
     */
    private String clgOffice;
    /**
     * 电话
     */
    private String clgTel;
    /**
     * 主页
     */
    private String clgHome;
    /**
     * 创建时间
     */
    private LocalDateTime clgCreated;
    /**
     * 上次修改
     */
    private LocalDateTime clgModified;
    private String teaId;
    private String principal;

    public PtCollegePageInfo(PtCollege college, @Nullable PtTeacher teacher) {
        this.clgCode = college.getClgCode();
        this.clgId = college.getClgId();
        this.clgName = college.getClgName();
        this.clgOffice = college.getClgOffice();
        this.clgTel = college.getClgTel();
        this.clgHome = college.getClgHome();
        this.clgCreated = college.getClgCreated();
        this.clgModified = college.getClgModified();
        if (teacher != null) {
            this.teaId = teacher.getTeaId();
            this.principal = teacher.getTeaName();
        }
    }

}
