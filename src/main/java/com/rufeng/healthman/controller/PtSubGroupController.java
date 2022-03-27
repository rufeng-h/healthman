package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtSubgroup;
import com.rufeng.healthman.service.PtSubgroupService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-27 19:30
 * @package com.rufeng.healthman.controller
 * @description TODO
 */
@RestController
@Validated
@RequestMapping("/api/subGroup")
@SecurityRequirement(name = JWT_SCHEME_NAME)
public class PtSubGroupController {
    private final PtSubgroupService ptSubgroupService;

    public PtSubGroupController(PtSubgroupService ptSubgroupService) {
        this.ptSubgroupService = ptSubgroupService;
    }

    @GetMapping("/list")
    public ApiResponse<List<PtSubgroup>> listSubGroup() {
        return ApiResponse.success(ptSubgroupService.listSubGroup());
    }
}
