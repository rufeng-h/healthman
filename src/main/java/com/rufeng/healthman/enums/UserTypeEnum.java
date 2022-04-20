package com.rufeng.healthman.enums;

import lombok.Getter;

/**
 * @author rufeng
 * @time 2022-03-09 18:11
 * @package com.rufeng.healthman.enums
 * @description 用户身份
 */
@Getter
public enum UserTypeEnum {
    /**
     * 用户类型
     */
    ADMIN("管理员"),
    STUDENT("学生"),
    TEACHER("教师");
    private final String identity;

    UserTypeEnum(String identity) {
        this.identity = identity;
    }
}
