package com.rufeng.healthman.pojo.BO.support;

import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.DO.PtUser;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author rufeng
 * @time 2022-03-09 18:29
 * @package com.rufeng.healthman.pojo.BO
 * @description 返回的userInfo
 */
@Data
public class UserInfo {
    private Long userId;
    private String username;
    private String avatar;
    private LocalDateTime createdTime;
    private LocalDateTime lastLoginTime;
    private String desp;

    public UserInfo(PtUser user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.createdTime = user.getCreatedTime();
        this.lastLoginTime = user.getLastLoginTime();
        this.desp = user.getDesp();
    }

    public UserInfo(PtStudent student) {
        this.userId = student.getNumber();
        this.username = student.getName();
        this.avatar = student.getAvatar();
        this.createdTime = student.getCreatedTime();
        this.lastLoginTime = student.getLastLoginTime();
        this.desp = student.getDesp();
    }
}
