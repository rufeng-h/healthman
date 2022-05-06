package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.ptdo.PtCompetency;
import com.rufeng.healthman.service.PtCompetencyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-04-10 13:10
 * @package com.rufeng.healthman.controller
 */
@RequestMapping("/api/comp")
@Validated
@SecurityRequirement(name = JWT_SCHEME_NAME)
@RestController
public class PtCompetencyController {
    private final PtCompetencyService ptCompetencyService;

    public PtCompetencyController(PtCompetencyService ptCompetencyService) {
        this.ptCompetencyService = ptCompetencyService;
    }

    @GetMapping("/list")
    public ApiResponse<List<PtCompetency>> listComp() {
        return ApiResponse.success(ptCompetencyService.listComp());
    }
}
