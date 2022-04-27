package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.dto.ptmeasurement.PtStuMeasurementPageInfo;
import com.rufeng.healthman.pojo.dto.ptstu.PtStudentPageInfo;
import com.rufeng.healthman.pojo.query.PtStudentQuery;
import com.rufeng.healthman.security.authority.ApiAuthority;
import com.rufeng.healthman.security.authority.Authority;
import com.rufeng.healthman.service.PtStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
@Tag(name = "Student Api", description = "学生接口")
@ApiAuthority
public class PtStudentController {
    private static final String FILE_TEMPLATE_NAME = URLEncoder.encode("学生模板.xlsx", StandardCharsets.UTF_8);
    private final PtStudentService ptStudentService;

    public PtStudentController(PtStudentService ptStudentService) {
        this.ptStudentService = ptStudentService;
    }

    @Operation(operationId = Authority.PtStudent.STUDENT_GET, summary = "学生")
    @GetMapping("/{stuId}")
    public ApiResponse<PtStuMeasurementPageInfo> getPtStuByNo(@PathVariable String stuId) {
        return ApiResponse.success(ptStudentService.getStuMsInfo(stuId));
    }

    @Operation(operationId = Authority.PtStudent.STUDENT_PAGE, summary = "学生列表")
    @GetMapping
    public ApiResponse<ApiPage<PtStudentPageInfo>> pageStudentInfo(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer pageSize,
            @Validated PtStudentQuery query) {
        return ApiResponse.success(ptStudentService.pageStudentInfo(page, pageSize, query));
    }

    @Operation(operationId = Authority.PtStudent.STUDENT_TEMPLATE, summary = "学生excel模板")
    @GetMapping("/template")
    public ResponseEntity<Resource> excelTemplate() {
        Resource resource = ptStudentService.fileTemplate();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + FILE_TEMPLATE_NAME + "\"")
                .body(resource);
    }

    @Operation(operationId = Authority.PtStudent.STUDENT_UPLOAD, summary = "学生上传")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Integer> uploadStudent(@RequestPart MultipartFile file, @RequestParam(required = false) String clsCode) {
        return ApiResponse.success(ptStudentService.uploadStudent(file, clsCode));
    }
}
