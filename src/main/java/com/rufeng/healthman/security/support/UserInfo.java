package com.rufeng.healthman.security.support;

import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.pojo.dto.support.RoleInfo;
import com.rufeng.healthman.pojo.ptdo.PtAdmin;
import com.rufeng.healthman.pojo.ptdo.PtStudent;
import com.rufeng.healthman.pojo.ptdo.PtTeacher;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-09 18:29
 * @package com.rufeng.healthman.pojo.BO
 * @description 返回的userInfo
 */
@Data
@NoArgsConstructor
@ToString
public abstract class UserInfo implements Serializable {
    private String userId;
    private String username;
    private String avatar;
    private GenderEnum gender;
    private LocalDateTime createdTime;
    private LocalDateTime lastLoginTime;
    private String desp;
    private UserTypeEnum userType;
    private List<RoleInfo> roles;
    private LocalDateTime lastModifyTime;

    public UserInfo(PtTeacher teacher, List<RoleInfo> roles) {
        this.userId = teacher.getTeaId();
        this.username = teacher.getTeaName();
        this.avatar = teacher.getAvatar();
        this.createdTime = teacher.getTeaCreated();
        this.lastLoginTime = teacher.getTeaLastLogin();
        this.desp = teacher.getTeaDesp();
        this.roles = roles;
        this.gender = teacher.getTeaGender();
        this.userType = UserTypeEnum.TEACHER;
        this.lastModifyTime = teacher.getTeaModified();
    }

    public UserInfo(PtStudent student) {
        this.userId = student.getStuId();
        this.username = student.getStuName();
        this.avatar = student.getAvatar();
        this.createdTime = student.getStuCreated();
        this.lastLoginTime = student.getStuLastLogin();
        this.desp = student.getStuDesp();
        this.gender = student.getStuGender();
        this.lastModifyTime = student.getStuModified();
        this.userType = UserTypeEnum.STUDENT;
    }

    public UserInfo(PtAdmin admin) {
        this.userId = admin.getAdminId();
        this.username = admin.getAdminName();
        this.createdTime = admin.getAdminCreated();
        this.lastLoginTime = admin.getAdminLastLogin();
        this.desp = admin.getAdminDesp();
        this.userType = UserTypeEnum.ADMIN;
        this.lastModifyTime = admin.getAdminModified();
        this.avatar = admin.getAvatar();
        this.gender = admin.getAdminGender();
    }

    public static String userKey(UserTypeEnum userType, String userId) {
        return userType + ":" + userId;
    }
}
