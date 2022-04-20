package com.rufeng.healthman.pojo.dto.ptteacher;

import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtTeacher;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-04-20 19:30
 * @package com.rufeng.healthman.pojo.dto.ptteacher
 * @description TODO
 */
@Data
public class PtTeacherPageInfo {
    private String teaId;
    private String teaName;
    /**
     * 头像
     */
    private String avatar;
    private String email;
    private LocalDateTime teaLastLogin;
    private String phone;
    /**
     * 所属学院
     */
    private String clgCode;
    private String clgName;
    private GenderEnum teaGender;
    private Boolean principal;
    private List<PtClass> classes;
    private LocalDateTime teaCreated;

    public PtTeacherPageInfo(PtTeacher teacher, String clgName, List<PtClass> classes) {
        this.teaId = teacher.getTeaId();
        this.teaName = teacher.getTeaName();
        this.avatar = teacher.getAvatar();
        this.email = teacher.getEmail();
        this.phone = teacher.getPhone();
        this.teaLastLogin = teacher.getTeaLastLogin();
        this.clgName = clgName;
        this.clgCode = teacher.getClgCode();
        this.principal = teacher.getPrincipal();
        this.teaGender = teacher.getTeaGender();
        this.teaCreated = teacher.getTeaCreated();
        this.classes = classes;
    }
}
