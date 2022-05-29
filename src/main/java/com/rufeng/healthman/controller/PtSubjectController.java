package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.aop.OperLogRecord;
import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.data.PtSubjectFormdata;
import com.rufeng.healthman.pojo.dto.ptsubject.SubjectDetail;
import com.rufeng.healthman.pojo.dto.ptsubject.SubjectInfo;
import com.rufeng.healthman.pojo.query.PtSubjectQuery;
import com.rufeng.healthman.security.authority.ApiAuthority;
import com.rufeng.healthman.security.authority.Authority;
import com.rufeng.healthman.service.PtSubjectService;
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
 * @time 2022-03-17 18:40
 * @package com.rufeng.healthman.controller
 */
@RequestMapping("/api/subject")
@Validated
@RestController
@SecurityRequirement(name = JWT_SCHEME_NAME)
@Tag(name = "Subject Api", description = "科目接口")
@ApiAuthority
public class PtSubjectController {
    private final PtSubjectService ptSubjectService;

    public PtSubjectController(PtSubjectService ptSubjectService) {
        this.ptSubjectService = ptSubjectService;
    }

    @OperLogRecord
    @Operation(operationId = Authority.PtSubject.SUBJECT_INSERT, summary = "新增科目")
    @PostMapping
    public ApiResponse<Boolean> addSubject(@RequestBody @Validated(Insert.class) PtSubjectFormdata data) {
        return ApiResponse.success(ptSubjectService.addSubject(data));
    }

    @Operation(operationId = Authority.PtSubject.SUBJECT_PAGE, summary = "科目列表")
    @GetMapping
    public ApiResponse<ApiPage<SubjectInfo>> pageSubInfo(@RequestParam(defaultValue = "1") @Min(1) Integer page,
                                                         @RequestParam(defaultValue = "8") @Min(1) @Max(100) Integer pageSize,
                                                         @Validated PtSubjectQuery query) {
        return ApiResponse.success(ptSubjectService.pageSubjectInfo(page, pageSize, query));
    }

    @OperLogRecord
    @PutMapping
    @Operation(operationId = Authority.PtSubject.SUBJECT_UPDATE, summary = "更新科目")
    public ApiResponse<Boolean> updateSubject(@RequestBody @Validated(Update.class) PtSubjectFormdata data) {
        return ApiResponse.success(ptSubjectService.updateSubject(data));
    }

    @OperLogRecord
    @DeleteMapping("/{subId}")
    @Operation(operationId = Authority.PtSubject.SUBJECT_DELETE, summary = "删除科目")
    public ApiResponse<Boolean> deleteSubject(@PathVariable Long subId) {
        return ApiResponse.success(ptSubjectService.deleteSubject(subId));
    }

    @Operation(operationId = Authority.PtSubject.SUBJECT_GET, summary = "科目")
    @GetMapping("/{subId}")
    public ApiResponse<SubjectDetail> getSubject(@PathVariable Long subId) {
        return ApiResponse.success(ptSubjectService.getSubjectDetail(subId));
    }
}
