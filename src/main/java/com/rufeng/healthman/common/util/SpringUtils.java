package com.rufeng.healthman.common.util;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author rufeng
 * @time 2022-04-16 10:58
 * @package com.rufeng.healthman.common.util
 * @description TODO
 */
@Component
public class SpringUtils implements EnvironmentAware {
    private static Environment env;

    public static boolean isDevMode() {
        String[] profiles = env.getActiveProfiles();
        for (String profile : profiles) {
            if ("dev".equals(profile)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }
}
