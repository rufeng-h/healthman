package com.rufeng.healthman.enums;

import lombok.Getter;

/**
 * @author rufeng
 * @time 2022-03-16 18:34
 * @package com.rufeng.healthman.enums
 * @description TODO
 */
@Getter
public enum RoleTypeEnum {
    /**
     * 角色类型
     */
    SYSTEM("系统管理员"),
    COLLEGE("学院管理员"),
    CLASS("班级管理员"),
    STUDENT("学生");
    private final String value;

    RoleTypeEnum(String value) {
        this.value = value;
    }
}
