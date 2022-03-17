package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.UserAddData;
import com.rufeng.healthman.pojo.DTO.ptuser.UserInfo;
import com.rufeng.healthman.pojo.Query.PtUserQuery;
import com.rufeng.healthman.service.PtUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-15 12:22
 * @package com.rufeng.healthman.controller
 * @description TODO
 */
@RestController
@Validated
@SecurityRequirement(name = JWT_SCHEME_NAME)
@RequestMapping("/api/user")
public class PtUserController {
    private final PtUserService ptUserService;

    public PtUserController(PtUserService ptUserService) {
        this.ptUserService = ptUserService;
    }

    @GetMapping
    public ApiResponse<ApiPage<UserInfo>> pageUser(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Range(min = 10, max = 100) Integer pageSize,
            @Validated PtUserQuery query) {
        return ApiResponse.success(ptUserService.pageUserInfo(page, pageSize, query));
    }

    @PostMapping
    public ApiResponse<UserInfo> addUser(@Validated @RequestBody UserAddData data) {
        return ApiResponse.success(ptUserService.addUser(data));
    }
}
