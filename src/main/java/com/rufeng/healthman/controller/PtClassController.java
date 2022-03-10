package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import com.rufeng.healthman.service.PtClassService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-06 22:17
 * @package com.rufeng.healthman.controller
 * @description 班级接口
 */
@RestController
@Validated
@RequestMapping("/api/class")
@SecurityRequirement(name = JWT_SCHEME_NAME)
@Tag(name = "PtClass Operation", description = "PtClass")
public class PtClassController {
    private final PtClassService ptClassService;

    public PtClassController(PtClassService ptClassService) {
        this.ptClassService = ptClassService;
    }

    @PostMapping("/list")
    public ApiResponse<ApiPage<PtClass>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody(required = false) PtClassQuery ptClassQuery) {
        if (ptClassQuery == null) {
            ptClassQuery = new PtClassQuery();
        }
        return ApiResponse.success(ptClassService.pageClass(page, pageSize, ptClassQuery));
    }
}
