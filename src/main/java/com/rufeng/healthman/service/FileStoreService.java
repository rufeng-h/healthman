package com.rufeng.healthman.service;

import com.rufeng.healthman.config.properties.HealthmanProperties;
import com.rufeng.healthman.exceptions.FileException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * @author rufeng
 * @time 2022-04-18 13:08
 * @package com.rufeng.healthman.service
 * @description TODO
 */
@Service
public class FileStoreService {
    private final Path avatarPath;
    private final Path uploadPath;

    public FileStoreService(HealthmanProperties healthmanProperties) {
        if (healthmanProperties.getUploadDir() != null) {
            this.uploadPath = Paths.get(healthmanProperties.getUploadDir());
            this.avatarPath = Paths.get(healthmanProperties.getUploadDir() + File.separator + "avatar");
        } else {
            this.avatarPath = this.uploadPath = null;
        }
        if (this.avatarPath != null && !Files.exists(this.avatarPath)) {
            try {
                Files.createDirectories(this.avatarPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public URI uploadAvatar(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new FileException("文件异常");
        }
        int i = filename.lastIndexOf(".");
        if (i == -1) {
            throw new FileException("文件异常");
        }
        String suffix = filename.substring(i);
        filename = DigestUtils.md5DigestAsHex((filename.substring(0, i) + LocalDateTime.now()).getBytes(StandardCharsets.UTF_8)) + suffix;
        Path dest = this.avatarPath.resolve(filename);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException("文件保存失败！");
        }
        return this.uploadPath.getParent().toUri().relativize(dest.toUri());
    }
}
