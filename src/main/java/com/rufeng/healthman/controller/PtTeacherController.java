package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.aop.OperLogRecord;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.dto.ptteacher.PtTeacherListInfo;
import com.rufeng.healthman.pojo.dto.ptteacher.PtTeacherPageInfo;
import com.rufeng.healthman.pojo.query.PtTeacherQuery;
import com.rufeng.healthman.security.authority.ApiAuthority;
import com.rufeng.healthman.security.authority.Authority;
import com.rufeng.healthman.service.PtTeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.validator.constraints.Range;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-15 12:22
 * @package com.rufeng.healthman.controller
 */
@RestController
@Validated
@SecurityRequirement(name = JWT_SCHEME_NAME)
@RequestMapping("/api/teacher")
@Tag(name = "Admin Api", description = "教师操作")
@ApiAuthority
public class PtTeacherController {
    private static final String TEMPLATE_FILE_NAME = URLEncoder.encode("教师模板文件.xlsx", StandardCharsets.UTF_8);
    private final PtTeacherService ptTeacherService;

    public PtTeacherController(PtTeacherService ptTeacherService) {
        this.ptTeacherService = ptTeacherService;
    }

    @Operation(operationId = Authority.PtTeacher.TEACHER_PAGE, summary = "教师列表")
    @GetMapping
    public ApiResponse<ApiPage<PtTeacherPageInfo>> pageTeacherInfo(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Range(min = 10, max = 100) Integer pageSize,
            @Validated PtTeacherQuery query) {
        return ApiResponse.success(ptTeacherService.pageTeacherInfo(page, pageSize, query));
    }

    @Operation(operationId = Authority.PtTeacher.PWD_RESET, summary = "重置教师密码")
    @PutMapping("/resetPwd/{teaId}")
    public ApiResponse<Boolean> resetPwd(@PathVariable String teaId) {
        return ApiResponse.success(ptTeacherService.resetPwd(teaId));
    }

    @OperLogRecord
    @Operation(operationId = Authority.PtTeacher.TEACHER_UPLOAD, summary = "上传教师数据")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Integer> uploadTeacher(@RequestPart MultipartFile file) {
        return ApiResponse.success(ptTeacherService.uploadTeacher(file));
    }

    /**
     * TODO 增加查询条件
     */
    @OperLogRecord
    @Operation(operationId = Authority.PtTeacher.TEACHER_LIST, summary = "所有教师")
    @GetMapping("/list")
    public ApiResponse<List<PtTeacherListInfo>> listTeacherListInfo() {
        return ApiResponse.success(ptTeacherService.listTeacherListInfo());
    }

    @Operation(operationId = Authority.PtTeacher.TEACHER_TEMPLATE, summary = "教师模板")
    @GetMapping("/template")
    public ResponseEntity<Resource> excelTemplate() {
        Resource resource = ptTeacherService.excelTemplate();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + TEMPLATE_FILE_NAME + "\"")
                .body(resource);
    }

    @OperLogRecord
    @Operation(operationId = Authority.PtTeacher.TEAHCER_DELETE, summary = "删除教师")
    @DeleteMapping("/{teaId}")
    public ApiResponse<Boolean> deleteTeacher(@PathVariable String teaId) {
        return ApiResponse.success(ptTeacherService.deleteTeacher(teaId));
    }
}
