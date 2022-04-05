package com.rufeng.healthman.pojo.DTO.ptstu;

import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.DTO.support.UserInfo;

/**
 * @author rufeng
 * @time 2022-03-21 17:56
 * @package com.rufeng.healthman.pojo.DTO.ptstu
 * @description 登录返回信息
 */
public class StudentUserInfo extends UserInfo {
    public StudentUserInfo(PtStudent student) {
        super(student);
    }
}
