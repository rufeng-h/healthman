package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DTO.ptuser.LoginResult;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.service.PtUserService;
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
@Tag(name = "common", description = "common")
public class CommonController {
    private final PtUserService userService;

    public CommonController(PtUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/logout")
    public ApiResponse<Void> logout() {
        userService.logout();
        return ApiResponse.success();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResult> login(
            @RequestBody @Validated LoginQuery loginQuery) {
        return ApiResponse.success(userService.login(loginQuery));
    }
}
