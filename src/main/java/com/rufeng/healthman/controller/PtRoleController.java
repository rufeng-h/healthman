package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.data.PtRoleFormdata;
import com.rufeng.healthman.security.support.RoleInfo;
import com.rufeng.healthman.security.authority.ApiAuthority;
import com.rufeng.healthman.security.authority.Authority;
import com.rufeng.healthman.service.PtRoleService;
import com.rufeng.healthman.validation.group.Insert;
import com.rufeng.healthman.validation.group.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<ApiPage<RoleInfo>> page(@RequestParam(defaultValue = "1") @Min(1) Integer page,
                                               @RequestParam(defaultValue = "6") @Min(1) @Max(100) Integer pageSize) {
        return ApiResponse.success(ptRoleService.page(page, pageSize));
    }

    @Operation(operationId = Authority.PtRole.ROLE_INSERT, summary = "添加角色")
    @PostMapping
    public ApiResponse<Boolean> addRole(@Validated(Insert.class) @RequestBody PtRoleFormdata formdata) {
        return ApiResponse.success(ptRoleService.addRole(formdata));
    }

    @Operation(operationId = Authority.PtRole.ROLE_UPDATE, summary = "更新角色")
    @PutMapping
    public ApiResponse<Boolean> updateRole(@Validated(Update.class) @RequestBody PtRoleFormdata formdata) {
        return ApiResponse.success(ptRoleService.updateRole(formdata));
    }

    @Operation(operationId = Authority.PtRole.ROLE_DELETE, summary = "删除角色")
    @DeleteMapping("/{roleId}")
    public ApiResponse<Boolean> updateRole(@PathVariable Long roleId) {
        return ApiResponse.success(ptRoleService.deleteRole(roleId));
    }
}
