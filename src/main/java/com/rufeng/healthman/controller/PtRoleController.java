package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.dto.ptteacher.PtRoleInfo;
import com.rufeng.healthman.security.authority.ApiAuthority;
import com.rufeng.healthman.security.authority.Authority;
import com.rufeng.healthman.service.PtRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-05-05 20:30
 * @package com.rufeng.healthman.controller
 */
@RequestMapping("/api/role")
@Validated
@RestController
@SecurityRequirement(name = JWT_SCHEME_NAME)
@Tag(name = "Role Api", description = "角色操作")
@ApiAuthority
public class PtRoleController {
    private final PtRoleService ptRoleService;

    public PtRoleController(PtRoleService ptRoleService) {
        this.ptRoleService = ptRoleService;
    }

    @Operation(operationId = Authority.PtRole.ROLE_PAGE, summary = "角色列表")
    @GetMapping
    public ApiResponse<ApiPage<PtRoleInfo>> page(@RequestParam(defaultValue = "1") @Min(1) Integer page,
                                                 @RequestParam(defaultValue = "6") @Min(1) @Max(100) Integer pageSize) {
        return ApiResponse.success(ptRoleService.page(page, pageSize));
    }
}
