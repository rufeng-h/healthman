package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.dto.ptcollege.PtCollegePageInfo;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.security.authority.ApiAuthority;
import com.rufeng.healthman.security.authority.Authority;
import com.rufeng.healthman.service.PtCollegeService;
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
 * @time 2022-03-12 9:53
 * @package com.rufeng.healthman.controller
 * @description TODO
 */
@Validated
@RestController
@Tag(name = "College Api", description = "学院操作接口")
@SecurityRequirement(name = JWT_SCHEME_NAME)
@RequestMapping("/api/college")
@ApiAuthority
public class PtCollegeController {
    private static final String FILE_TEMPLATE_NAME = URLEncoder.encode("学院模板文件.xlsx", StandardCharsets.UTF_8);
    private final PtCollegeService ptCollegeService;

    public PtCollegeController(PtCollegeService ptCollegeService) {
        this.ptCollegeService = ptCollegeService;
    }

//    @GetMapping("/tree")
//    public ApiResponse<List<PtCollegeTreeItem>> treeCollege() {
//        List<PtCollege> colleges = ptCollegeService.listCollege();
//        List<PtCollegeTreeItem> treeItems =
//                colleges.stream().map(PtCollegeTreeItem::new).collect(Collectors.toList());
//        return ApiResponse.success(treeItems);
//    }


    @Operation(operationId = Authority.PtCollege.COLLEGE_PAGE, summary = "学院列表")
    @GetMapping
    public ApiResponse<ApiPage<PtCollegePageInfo>> pageCollegeInfo(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        return ApiResponse.success(ptCollegeService.pageCollegeInfo(page, pageSize));
    }

    @Operation(operationId = Authority.PtCollege.COLLEGE_LIST, summary = "所有学院")
    @GetMapping("/list")
    public ApiResponse<List<PtCollege>> listCollege() {
        return ApiResponse.success(ptCollegeService.listCollege());
    }

    @Operation(operationId = Authority.PtCollege.COLLEGE_UPLOAD, summary = "学院上传")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Integer> uploadCollege(@RequestPart MultipartFile file) {
        Integer count = ptCollegeService.uploadCollege(file);
        return ApiResponse.success(count);
    }

    @Operation(operationId = Authority.PtCollege.COLLEGE_GET, summary = "学院")
    @GetMapping("{clgCode}")
    public ApiResponse<PtCollege> getCollege(@PathVariable String clgCode) {
        return ApiResponse.success(ptCollegeService.getCollege(clgCode));
    }

    @Operation(operationId = Authority.PtCollege.COLLEGE_TEMPLATE, summary = "学院模板文件")
    @GetMapping("/template")
    public ResponseEntity<Resource> fileTemplate() {
        Resource resource = ptCollegeService.fileTemplate();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + FILE_TEMPLATE_NAME + "\"")
                .body(resource);
    }
}
