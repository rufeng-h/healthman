package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.ApiPage;
import com.rufeng.healthman.common.ApiResponse;
import com.rufeng.healthman.domain.PtClass;
import com.rufeng.healthman.service.PtClassService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author rufeng
 * @time 2022-03-06 22:17
 * @package com.rufeng.healthman.controller
 * @description 班级接口
 */
@RestController
@Validated
@RequestMapping("/api/class")
@SecurityRequirement(name = "JWT")
@Tag(name = "PtClass Operation", description = "PtClass")
public class PtClassController {
    private final PtClassService ptClassService;

    public PtClassController(PtClassService ptClassService) {
        this.ptClassService = ptClassService;
    }

    @PostMapping("/list")
    public ApiResponse<ApiPage<PtClass>> list(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody(required = false) PtClass ptClass) {
        if (ptClass == null) {
            ptClass = new PtClass();
        }
        return ApiResponse.success(ptClassService.pagePtClass(current, pageSize, ptClass));
    }
}
