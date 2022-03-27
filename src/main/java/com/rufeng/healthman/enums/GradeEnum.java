package com.rufeng.healthman.enums;

import lombok.Getter;

/**
 * @author rufeng
 * @time 2022-03-22 21:59
 * @package com.rufeng.healthman.enums
 * @description TODO
 */
@Getter
public enum GradeEnum {
    /**
     * 年级，一年级-大四
     */
    PRIMARY_ONE("一年级", 1),
    PRIMARY_TWO("二年级", 2),
    PRIMARY_THREE("三年级", 3),
    PRIMARY_FOUR("四年级", 4),
    PRIMARY_FIVE("五年级", 5),
    PRIMARY_SIX("六年级", 6),
    JUNIOR_ONE("初一", 7),
    JUNIOR_TWO("初二", 8),
    JUNIOR_THREE("初三", 9),
    SENIOR_ONE("高一", 10),
    SENIRO_TWO("高二", 11),
    SENIOR_THREE("高三", 12),
    COLLEGE_ONE("大一", 13),
    COLLEGE_TWO("大二", 14),
    COLLEGE_THREE("大三", 15),
    COLLEGE_FOUR("大四", 16);
    public static final int MAX_GRADE = 16;
    public static final int MIN_GRADE = 1;
    private final String grade;
    private final Integer value;

    GradeEnum(String grade, Integer value) {
        this.grade = grade;
        this.value = value;
    }
}
