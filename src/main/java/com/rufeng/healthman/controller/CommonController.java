package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.dto.support.LoginResult;
import com.rufeng.healthman.pojo.dto.support.UserInfo;
import com.rufeng.healthman.pojo.query.LoginQuery;
import com.rufeng.healthman.service.FileStoreService;
import com.rufeng.healthman.service.PtCommonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

/**
 * @author rufeng
 * @time 2022-03-05 22:43
 * @package com.rufeng.healthman.controller
 * @description .
 */
@RestController
@Validated
@Tag(name = "Common Api", description = "通用操作")
public class CommonController {
    private final PtCommonService ptCommonService;
    private final FileStoreService fileStoreService;

    public CommonController(PtCommonService ptCommonService, FileStoreService fileStoreService) {
        this.ptCommonService = ptCommonService;
        this.fileStoreService = fileStoreService;
    }

    @GetMapping("/api/logout")
    public ApiResponse<Void> logout() {
        ptCommonService.logout();
        return ApiResponse.success();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResult> login(
            @RequestBody @Validated LoginQuery loginQuery) {
        return ApiResponse.success(ptCommonService.login(loginQuery));
    }

    @GetMapping("/api/userInfo")
    public ApiResponse<UserInfo> userInfo() {
        return ApiResponse.success(ptCommonService.userInfo());
    }

    @PostMapping(value = "/upload/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<URI> uploadAvatar(@RequestPart MultipartFile file) {
        return ApiResponse.success(fileStoreService.uploadAvatar(file));
    }
}
