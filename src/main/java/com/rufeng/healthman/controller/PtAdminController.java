package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.data.PtAdminFormdata;
import com.rufeng.healthman.pojo.DTO.ptadmin.AdminInfo;
import com.rufeng.healthman.pojo.DTO.support.UserInfo;
import com.rufeng.healthman.pojo.Query.PtAdminQuery;
import com.rufeng.healthman.service.PtAdminService;
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
@RequestMapping("/api/admin")
public class PtAdminController {
    private final PtAdminService ptAdminService;

    public PtAdminController(PtAdminService ptAdminService) {
        this.ptAdminService = ptAdminService;
    }

//    @PreAuthorize("hasAuthority('null:select')")
    @GetMapping
    public ApiResponse<ApiPage<AdminInfo>> pageAdmin(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Range(min = 10, max = 100) Integer pageSize,
            @Validated PtAdminQuery query) {
        return ApiResponse.success(ptAdminService.pageAdminInfo(page, pageSize, query));
    }

    @PostMapping
    public ApiResponse<UserInfo> addAdmin(@Validated @RequestBody PtAdminFormdata data) {
        return ApiResponse.success(ptAdminService.addAdmin(data));
    }
}
