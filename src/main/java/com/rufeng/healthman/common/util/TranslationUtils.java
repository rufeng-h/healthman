package com.rufeng.healthman.common.util;

import com.rufeng.healthman.enums.GenderEnum;
import com.rufeng.healthman.enums.GradeEnum;
import com.rufeng.healthman.exceptions.TranslateException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rufeng
 * @time 2022-04-15 22:36
 * @package com.rufeng.healthman.common.util
 * @description TODO
 */
public class TranslationUtils {
    private static final Map<String, GenderEnum> STR2GENDER = new HashMap<>(2);

    private static final Map<String, Integer> STR2GRADE = new HashMap<>(16);
    private static final Map<Integer, String> GRADE2STR = new HashMap<>(16);

    static {
        GradeEnum[] gradeEnums = GradeEnum.class.getEnumConstants();
        for (GradeEnum gradeEnum : gradeEnums) {
            STR2GRADE.put(gradeEnum.getGrade(), gradeEnum.getValue());
            GRADE2STR.put(gradeEnum.getValue(), gradeEnum.getGrade());
        }
    }

    static {
        GenderEnum[] genderEnums = GenderEnum.class.getEnumConstants();
        for (GenderEnum genderEnum : genderEnums) {
            STR2GENDER.put(genderEnum.getGender(), genderEnum);
        }

    }

    public static GenderEnum translateGender(String gender) {
        GenderEnum genderEnum = STR2GENDER.get(gender);
        if (genderEnum == null) {
            throw new TranslateException("错误的性别：" + gender);
        }
        return genderEnum;
    }

    public static Integer translateGrade(String grade) {
        Integer value = STR2GRADE.get(grade);
        if (value == null) {
            throw new TranslateException("年级错误：" + grade);
        }
        return value;
    }

    public static String translateGrade(Integer grade) {
        String value = GRADE2STR.get(grade);
        if (value == null) {
            throw new TranslateException("年级错误：" + grade);
        }
        return value;
    }
}
