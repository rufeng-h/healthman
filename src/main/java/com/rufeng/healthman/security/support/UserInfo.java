package com.rufeng.healthman.security.support;

import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.enums.RoleTypeEnum;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.pojo.dto.support.RoleInfo;
import com.rufeng.healthman.pojo.ptdo.PtAdmin;
import com.rufeng.healthman.pojo.ptdo.PtStudent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-09 18:29
 * @package com.rufeng.healthman.pojo.BO
 * @description 返回的userInfo
 */
@Data
@NoArgsConstructor
public abstract class UserInfo implements Serializable {
    private String userId;
    private String username;
    private String avatar;
    private GenderEnum gender;
    private LocalDate birth;
    private LocalDateTime createdTime;
    private LocalDateTime lastLoginTime;
    private String desp;
    private UserTypeEnum userType;
    private List<RoleInfo> roles;
    private LocalDateTime lastModifyTime;

    public UserInfo(PtAdmin user, List<RoleInfo> roles) {
        this.userId = user.getAdminId();
        this.username = user.getAdminName();
        this.avatar = user.getAvatar();
        this.createdTime = user.getAdminCreated();
        this.lastLoginTime = user.getAdminLastLogin();
        this.desp = user.getAdminDesp();
        this.roles = roles;
        this.gender = user.getAdminGender();
        this.birth = user.getAdminBirth();
        this.userType = UserTypeEnum.ADMIN;
        this.lastModifyTime = user.getAdminModified();
    }

    public UserInfo(PtStudent student) {
        this.userId = student.getStuId();
        this.username = student.getStuName();
        this.avatar = student.getAvatar();
        this.createdTime = student.getStuCreated();
        this.lastLoginTime = student.getStuLastLogin();
        this.desp = student.getStuDesp();
        this.gender = student.getStuGender();
        this.birth = student.getStuBirth();
        this.lastModifyTime = student.getStuModified();

        RoleInfo role = new RoleInfo("学生", RoleTypeEnum.STUDENT);

        this.roles = Collections.singletonList(role);
        this.userType = UserTypeEnum.STUDENT;
    }
}
