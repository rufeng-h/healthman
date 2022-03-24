package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.DTO.ptclass.ClassInfo;
import com.rufeng.healthman.pojo.DTO.ptclass.PtClassTreeItem;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import com.rufeng.healthman.service.PtClassService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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
    private static final String TEMPLATE_FILE_NAME = URLEncoder.encode("班级模板文件.xlsx", StandardCharsets.UTF_8);

    public PtClassController(PtClassService ptClassService) {
        this.ptClassService = ptClassService;
    }

    @GetMapping
    public ApiResponse<ApiPage<ClassInfo>> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @Validated PtClassQuery ptClassQuery) {
        return ApiResponse.success(ptClassService.pageClassInfo(page, pageSize, ptClassQuery));
    }

    @GetMapping("/tree")
    public ApiResponse<List<PtClassTreeItem>> tree(@Validated PtClassQuery query) {
        List<PtClass> classList = ptClassService.listClass(query);
        List<PtClassTreeItem> items = classList.stream().map(PtClassTreeItem::new).collect(Collectors.toList());
        return ApiResponse.success(items);
    }

    @GetMapping("/list")
    public ApiResponse<List<PtClass>> list(@Validated PtClassQuery query) {
        return ApiResponse.success(ptClassService.listClass(query));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Integer> uploadClass(@RequestPart MultipartFile file) {
        int count = ptClassService.uploadClass(file);
        return ApiResponse.success(count);
    }

    @PreAuthorize("authentication.authorities.size() != 0")
    @GetMapping(value = "/template")
    public ResponseEntity<Resource> downloadTemplate() {
        Resource resource = ptClassService.fileTemplate();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + TEMPLATE_FILE_NAME + "\"")
                .body(resource);
    }

    @GetMapping("/grade/list")
    public ApiResponse<List<Integer>> listGrade(@Validated PtClassQuery query){
        return ApiResponse.success(ptClassService.listGrade(query));
    }
}
