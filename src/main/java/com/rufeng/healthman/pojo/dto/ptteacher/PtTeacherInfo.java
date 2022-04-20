package com.rufeng.healthman.pojo.dto.ptteacher;

import com.rufeng.healthman.pojo.ptdo.PtTeacher;
import com.rufeng.healthman.security.support.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Collections;

/**
 * @author rufeng
 * @time 2022-03-21 16:08
 * @description TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class PtTeacherInfo extends UserInfo {
    private String email;
    private String phone;
    private String clgCode;
    private String clgName;
    private LocalDate birth;

    public PtTeacherInfo(PtTeacher teacher, String clgName) {
        super(teacher, Collections.emptyList());
        this.clgName = clgName;
        this.clgCode = teacher.getClgCode();
        this.birth = teacher.getTeaBirth();
        this.email = teacher.getEmail();
        this.phone = teacher.getPhone();
    }
}
