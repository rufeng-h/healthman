package com.rufeng.healthman.pojo.DTO.ptuser;

import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.DO.PtUser;
import lombok.Data;

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
public class UserInfo {
    private static final Role STU_ROLE =
            new Role("学生", "student");
    private Long userId;
    private String email;
    private String phone;
    private String username;
    private String avatar;
    private LocalDateTime createdTime;
    private LocalDateTime lastLoginTime;
    private String desp;
    private List<Role> roles;

    public UserInfo(PtUser user, List<Role> roles) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.createdTime = user.getCreatedTime();
        this.lastLoginTime = user.getLastLoginTime();
        this.desp = user.getDesp();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.roles = roles;
    }

    public UserInfo(PtStudent student) {
        this.userId = student.getNumber();
        this.username = student.getName();
        this.avatar = student.getAvatar();
        this.createdTime = student.getCreatedTime();
        this.lastLoginTime = student.getLastLoginTime();
        this.desp = student.getDesp();
        this.email = student.getEmail();
        this.phone = student.getPhone();
        this.roles = Collections.singletonList(STU_ROLE);
    }
}
