package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.data.PtClassFormdata;
import com.rufeng.healthman.pojo.dto.ptclass.PtClassPageInfo;
import com.rufeng.healthman.pojo.ptdo.PtClass;
import com.rufeng.healthman.pojo.query.PtClassQuery;
import com.rufeng.healthman.security.authority.ApiAuthority;
import com.rufeng.healthman.security.authority.Authority;
import com.rufeng.healthman.service.PtClassService;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
@Tag(name = "Class Api", description = "班级操作")
@ApiAuthority
public class PtClassController {
    private static final String TEMPLATE_FILE_NAME = URLEncoder.encode("班级模板文件.xlsx", StandardCharsets.UTF_8);
    private final PtClassService ptClassService;

    public PtClassController(PtClassService ptClassService) {
        this.ptClassService = ptClassService;
    }

    @Operation(operationId = Authority.PtClass.CLASS_PAGE, summary = "班级列表")
    @GetMapping
    public ApiResponse<ApiPage<PtClassPageInfo>> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @Validated PtClassQuery ptClassQuery) {
        return ApiResponse.success(ptClassService.pageClassInfo(page, pageSize, ptClassQuery));
    }

    @Operation(operationId = Authority.PtClass.CLASS_LIST, summary = "所有班级")
    @GetMapping("/list")
    public ApiResponse<List<PtClass>> list(@Validated PtClassQuery query) {
        return ApiResponse.success(ptClassService.listClass(query));
    }

    @Operation(operationId = Authority.PtClass.CLASS_UPLOAD, summary = "上传班级")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Integer> uploadClass(@RequestPart MultipartFile file,
                                            @RequestParam(required = false) String clgCode) {
        int count = ptClassService.uploadClass(file, clgCode);
        return ApiResponse.success(count);
    }

    @Operation(operationId = Authority.PtClass.CLASS_TEMPLATE, summary = "班级excel模板")
    @GetMapping(value = "/template")
    public ResponseEntity<Resource> downloadTemplate() {
        Resource resource = ptClassService.fileTemplate();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + TEMPLATE_FILE_NAME + "\"")
                .body(resource);
    }


    @Operation(operationId = Authority.PtClass.CLASS_GET, summary = "班级")
    @GetMapping("/{clsCode}")
    public ApiResponse<PtClass> getPtClass(@PathVariable String clsCode) {
        return ApiResponse.success(ptClassService.getPtClass(clsCode));
    }

    @Operation(operationId = Authority.PtClass.CLASS_DELETE, summary = "删除班级")
    @DeleteMapping("/{clsCode}")
    public ApiResponse<Boolean> deletePtClass(@PathVariable String clsCode) {
        return ApiResponse.success(ptClassService.deletePtClass(clsCode));
    }


    @Operation(operationId = Authority.PtClass.CLASS_UPDATE, summary = "更新班级信息")
    @PutMapping
    public ApiResponse<Boolean> updatePtClass(@RequestBody @Validated PtClassFormdata classFormdata) {
        return ApiResponse.success(ptClassService.updatePtClass(classFormdata));
    }
}
