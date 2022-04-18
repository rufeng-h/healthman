package com.rufeng.healthman.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * @author rufeng
 * @time 2022-04-18 16:11
 * @package com.rufeng.healthman.config.properties
 * @description TODO
 */
@ConstructorBinding
@Getter
@ConfigurationProperties(prefix = "healthman")
@Validated
public class HealthmanProperties {
    private final String uploadDir;
    private final Duration uploadCacheControl;
    private final String urlSeperator = "/";

    public HealthmanProperties(@NotEmpty String uploadDir, Duration uploadCacheControl) {
        this.uploadDir = uploadDir;
        this.uploadCacheControl = uploadCacheControl;
        if (this.uploadDir != null) {
            Path path = Paths.get(this.uploadDir);
            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
