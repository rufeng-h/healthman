package com.rufeng.healthman.config;

import com.rufeng.healthman.config.properties.HealthmanProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author rufeng
 * @time 2022-04-18 15:53
 * @package com.rufeng.healthman.config
 * @description TODO
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private static final String FILE_PROTOCOL = "file:///";
    private final HealthmanProperties healthmanProperties;

    public MvcConfig(HealthmanProperties healthmanProperties) {
        this.healthmanProperties = healthmanProperties;
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        if (healthmanProperties.getUploadDir() != null) {
            registry.addResourceHandler("/upload/**")
                    .setCacheControl(CacheControl.maxAge(healthmanProperties.getUploadCacheControl()))
                    .addResourceLocations(ensureSuffix(FILE_PROTOCOL + healthmanProperties.getUploadDir()));
        }
    }

    private String ensureSuffix(String path) {
        if (!path.endsWith(healthmanProperties.getUrlSeperator())) {
            return path + healthmanProperties.getUrlSeperator();
        }
        return path;
    }
}
