package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.dto.support.LoginResult;
import com.rufeng.healthman.pojo.dto.support.UserInfo;
import com.rufeng.healthman.pojo.query.LoginQuery;
import com.rufeng.healthman.service.PtCommonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    public CommonController(PtCommonService ptCommonService) {
        this.ptCommonService = ptCommonService;
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
}
