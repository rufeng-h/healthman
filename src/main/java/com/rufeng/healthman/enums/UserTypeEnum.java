package com.rufeng.healthman.enums;

/**
 * @author rufeng
 * @time 2022-03-09 18:11
 * @package com.rufeng.healthman.enums
 * @description 用户身份
 */
public enum UserTypeEnum {
    /**
     * 用户类型
     */
    ADMIN("管理员"),
    STUDENT("学生");
    private final String identity;

    UserTypeEnum(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }
}
