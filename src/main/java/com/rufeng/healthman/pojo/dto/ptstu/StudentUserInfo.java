package com.rufeng.healthman.pojo.dto.ptstu;

import com.rufeng.healthman.security.support.UserInfo;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.pojo.ptdo.PtStudent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rufeng
 * @time 2022-03-21 17:56
 * @package com.rufeng.healthman.pojo.DTO.ptstu
 * @description 登录返回信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentUserInfo extends UserInfo {
    private String clgName;
    private String clsName;

    public StudentUserInfo(PtStudent student, PtClass ptClass, PtCollege college) {
        super(student);
        this.clgName = college.getClgName();
        this.clsName = ptClass.getClsName();
    }
}
