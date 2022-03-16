package com.rufeng.healthman.enums;

import lombok.Getter;

/**
 * @author rufeng
 * @time 2022-03-13 19:34
 * @package com.rufeng.healthman.enums
 * @description TODO
 */
@Getter
public enum GenderEnum {
    /**
     * 性别
     */
    M("男"), F("女");
    private final String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }
}
