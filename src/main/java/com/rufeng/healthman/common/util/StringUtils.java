package com.rufeng.healthman.common.util;

/**
 * @author rufeng
 * @time 2022-04-16 13:33
 * @package com.rufeng.healthman.common.util
 * @description TODO
 */
public class StringUtils {
    public static boolean isEmptyOrBlank(CharSequence cs) {
        return cs == null || cs.length() == 0 || org.apache.commons.lang3.StringUtils.isBlank(cs);
    }


    public static boolean isLetterNumeric(CharSequence cs) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(cs)) {
            return false;
        } else {
            int len = cs.length();
            for (int i = 0; i < len; i++) {
                char c = cs.charAt(i);
                if (!Character.isDigit(c) && !Character.isUpperCase(c) && !Character.isLowerCase(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 合法的数字形式，不含指数形式，可以带负号，不能正号
     * -0.1020
     * -000.12
     * 000.
     * 0.
     */
    public static boolean isValidNumber(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        int len = value.length();
        int i = 0;
        boolean dot = false;
        boolean neg = false;
        if (value.charAt(0) == '-') {
            i += 1;
            neg = true;
        }
        if (neg && (i == len || !Character.isDigit(value.charAt(i)))) {
            return false;
        }
        /* 前导0 */
        while (i < len && value.charAt(i) == '0') {
            i++;
        }
        while (i < len) {
            char c = value.charAt(i);
            if (c == '.') {
                if (dot || i == 0) {
                    return false;
                } else {
                    dot = true;
                }
            } else if (!Character.isDigit(c)) {
                return false;
            }
            i++;
        }
        return true;
    }
}
