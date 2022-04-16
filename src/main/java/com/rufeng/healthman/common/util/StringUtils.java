package com.rufeng.healthman.common.util;

/**
 * @author rufeng
 * @time 2022-04-16 13:33
 * @package com.rufeng.healthman.common.util
 * @description TODO
 */
public class StringUtils {
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isLetterNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
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
}
