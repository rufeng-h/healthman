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
 * @description 服务器文件操作
 */
@Service
public class FileService {
    private final Path avatarPath;
    private final Path workDir;

    public FileService(HealthmanProperties healthmanProperties) {
        if (healthmanProperties.getWorkDir() != null) {
            this.avatarPath = Paths.get(healthmanProperties.getUploadDir() + File.separator + "avatar");
            this.workDir = Paths.get(healthmanProperties.getWorkDir());
        } else {
            this.avatarPath = this.workDir = null;
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
        filename = DigestUtils.md5DigestAsHex(
                (filename.substring(0, i) + LocalDateTime.now()).getBytes(StandardCharsets.UTF_8)) + suffix;
        Path dest = this.avatarPath.resolve(filename);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException("文件保存失败！");
        }
        return this.workDir.toUri().relativize(dest.toUri());
    }

    /**
     * 删除本地文件
     * @return 本地不存在、删除成功返回true
     */
    public boolean remove(String path) {
        URI uri = URI.create(path);
        if (uri.getScheme() != null) {
            return true;
        }
        Path target = this.workDir.resolve(path);
        if (!Files.exists(target)) {
            return true;
        }
        try {
            Files.delete(target);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
