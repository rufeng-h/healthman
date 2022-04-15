package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.data.PtAdminFormdata;
import com.rufeng.healthman.pojo.dto.ptadmin.AdminInfo;
import com.rufeng.healthman.pojo.query.PtAdminQuery;
import com.rufeng.healthman.service.PtAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-15 12:22
 * @package com.rufeng.healthman.controller
 * @description TODO
 */
@RestController
@Validated
@SecurityRequirement(name = JWT_SCHEME_NAME)
@RequestMapping("/api/admin")
public class PtAdminController {
    private static final String TEMPLATE_FILE_NAME = URLEncoder.encode("管理员模板文件.xlsx", StandardCharsets.UTF_8);
    private final PtAdminService ptAdminService;

    public PtAdminController(PtAdminService ptAdminService) {
        this.ptAdminService = ptAdminService;
    }

    //    @PreAuthorize("hasAuthority('null:select')")
    @GetMapping
    public ApiResponse<ApiPage<AdminInfo>> pageAdmin(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Range(min = 10, max = 100) Integer pageSize,
            @Validated PtAdminQuery query) {
        return ApiResponse.success(ptAdminService.pageAdminInfo(page, pageSize, query));
    }

    @PostMapping
    public ApiResponse<Boolean> addAdmin(@Validated @RequestBody PtAdminFormdata data) {
        return ApiResponse.success(ptAdminService.addAdmin(data));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Integer> uploadAdmin(@RequestPart MultipartFile file) {
        return ApiResponse.success(ptAdminService.uploadAdmin(file));
    }

    @GetMapping("/template")
    public ResponseEntity<Resource> excelTemplate() {
        Resource resource = ptAdminService.excelTemplate();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + TEMPLATE_FILE_NAME + "\"")
                .body(resource);
    }
}
