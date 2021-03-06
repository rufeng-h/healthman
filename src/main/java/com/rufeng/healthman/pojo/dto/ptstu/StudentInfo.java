package com.rufeng.healthman.pojo.dto.ptstu;

import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.pojo.ptdo.PtStudent;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author rufeng
 * @time 2022-03-23 14:55
 * @package com.rufeng.healthman.pojo.DTO.ptstu
 * @description 学生信息
 */
@Data
public class StudentInfo {
    private Long sid;
    private String stuId;
    private String stuName;
    private LocalDate stuBirth;
    private GenderEnum stuGender;
    private String clsCode;
    private LocalDateTime stuCreated;
    private LocalDateTime stuLastLogin;
    private String avatar;
    private String stuDesp;

    private String clsName;
    private String clgCode;
    private String clgName;

    public StudentInfo(PtStudent student, String clsName, String clgCode, String clgName) {
        this.sid = student.getSid();
        this.stuId = student.getStuId();
        this.stuName = student.getStuName();
        this.stuBirth = student.getStuBirth();
        this.stuGender = student.getStuGender();
        this.clsCode = student.getClsCode();
        this.stuCreated = student.getStuCreated();
        this.stuLastLogin = student.getStuLastLogin();
        this.avatar = student.getAvatar();
        this.stuDesp = student.getStuDesp();
        this.clsName = clsName;
        this.clgCode = clgCode;
        this.clgName = clgName;
    }

    public StudentInfo(PtStudent student, String clsName) {
        this(student, clsName, null, null);
    }
}
