package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtStudent;
import com.rufeng.healthman.pojo.Query.PtStudentQuery;
import com.rufeng.healthman.service.PtStudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-07 16:18
 * @package com.rufeng.healthman.controller
 * @description stu
 */
@RestController
@RequestMapping("/api/student")
@Validated
@SecurityRequirement(name = JWT_SCHEME_NAME)
public class PtStudentController {
    private final PtStudentService ptStudentService;

    public PtStudentController(PtStudentService ptStudentService) {
        this.ptStudentService = ptStudentService;
    }

    @GetMapping("/{number}")
    public ApiResponse<PtStudent> getPtStuByNo(@Min(1) @PathVariable Long number) {
        return ApiResponse.success(ptStudentService.getStudent(number));
    }

    @GetMapping
    public ApiResponse<ApiPage<PtStudent>> pageStudent(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer pageSize,
            @Validated PtStudentQuery query) {
        return ApiResponse.success(ptStudentService.pageStudent(page, pageSize, query));
    }
}
