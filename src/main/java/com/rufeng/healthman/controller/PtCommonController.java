package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.data.LoginFormdata;
import com.rufeng.healthman.pojo.data.PtUserFormdata;
import com.rufeng.healthman.pojo.data.UpdatePwdFormdata;
import com.rufeng.healthman.pojo.dto.support.LoginResult;
import com.rufeng.healthman.security.support.UserInfo;
import com.rufeng.healthman.service.FileService;
import com.rufeng.healthman.service.PtCommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-05 22:43
 * @package com.rufeng.healthman.controller
 * @description .
 */
@RestController
@Validated
@Tag(name = "Common Api", description = "通用操作")
public class PtCommonController {
    private final PtCommonService ptCommonService;
    private final FileService fileService;

    public PtCommonController(PtCommonService ptCommonService, FileService fileService) {
        this.ptCommonService = ptCommonService;
        this.fileService = fileService;
    }

    @Operation
    @SecurityRequirement(name = JWT_SCHEME_NAME)
    @GetMapping("/api/logout")
    public ApiResponse<Void> logout() {
        ptCommonService.logout();
        return ApiResponse.success();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResult> login(
            @RequestBody @Validated LoginFormdata loginFormdata) {
        return ApiResponse.success(ptCommonService.login(loginFormdata));
    }

    @SecurityRequirement(name = JWT_SCHEME_NAME)
    @GetMapping("/api/userInfo")
    public ApiResponse<UserInfo> userInfo() {
        return ApiResponse.success(ptCommonService.getUserInfo());
    }

    @SecurityRequirement(name = JWT_SCHEME_NAME)
    @PostMapping(value = "/upload/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<URI> uploadAvatar(@RequestPart MultipartFile file) {
        return ApiResponse.success(fileService.uploadAvatar(file));
    }

    @SecurityRequirement(name = JWT_SCHEME_NAME)
    @PutMapping("/api/password")
    @Operation(description = "重置密码")
    public ApiResponse<Boolean> updatePassword(@Validated @RequestBody UpdatePwdFormdata formdata) {
        return ApiResponse.success(ptCommonService.updatePwd(formdata));
    }

    @SecurityRequirement(name = JWT_SCHEME_NAME)
    @PutMapping("/api/user")
    @Operation(description = "更新用户信息")
    public ApiResponse<Boolean> updateUser(@RequestBody @Validated PtUserFormdata formdata) {
        return ApiResponse.success(ptCommonService.updateUser(formdata));
    }
}
