package com.rufeng.healthman.pojo.dto.support;

import com.rufeng.healthman.enums.RoleTypeEnum;
import com.rufeng.healthman.pojo.ptdo.PtAdmin;
import com.rufeng.healthman.pojo.ptdo.PtStudent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-09 18:29
 * @package com.rufeng.healthman.pojo.BO
 * @description 返回的userInfo
 */
@Data
@NoArgsConstructor
public abstract class UserInfo {
    private String userId;
    private String username;
    private String avatar;
    private Date createdTime;
    private Date lastLoginTime;
    private String desp;
    private List<RoleInfo> roles;

    public UserInfo(PtAdmin user, List<RoleInfo> roles) {
        this.userId = user.getAdminId();
        this.username = user.getAdminName();
        this.avatar = user.getAvatar();
        this.createdTime = user.getAdminCreated();
        this.lastLoginTime = user.getAdminLastLogin();
        this.desp = user.getAdminDesp();
        this.roles = roles;
    }

    public UserInfo(PtStudent student) {
        this.userId = student.getStuId();
        this.username = student.getStuName();
        this.avatar = student.getAvatar();
        this.createdTime = student.getStuCreated();
        this.lastLoginTime = student.getStuLastLogin();
        this.desp = student.getStuDesp();

        RoleInfo role = new RoleInfo("学生", RoleTypeEnum.STUDENT);

        this.roles = Collections.singletonList(role);
    }
}
