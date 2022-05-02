package com.rufeng.healthman.pojo.dto.ptstu;

import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.pojo.ptdo.PtStudent;
import com.rufeng.healthman.security.support.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author rufeng
 * @time 2022-03-21 17:56
 * @package com.rufeng.healthman.pojo.DTO.ptstu
 * @description 登录返回信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class PtStudentInfo extends UserInfo {
    private String clgName;
    private String clsName;
    private LocalDate birth;

    public PtStudentInfo(PtStudent student, PtClass ptClass, @Nullable PtCollege college, Set<String> authorities) {
        super(student, authorities);
        if (college != null) {
            this.clgName = college.getClgName();
        }
        this.clsName = ptClass.getClsName();
        this.birth = student.getStuBirth();
    }
}
