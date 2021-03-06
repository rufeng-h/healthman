package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.ptdo.PtCollege;
import com.rufeng.healthman.pojo.dto.ptcollege.PtCollegeTreeItem;
import com.rufeng.healthman.service.PtCollegeService;
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
import java.util.stream.Collectors;

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
public class PtCollegeController {
    private static final String FILE_TEMPLATE_NAME = URLEncoder.encode("学院模板文件.xlsx", StandardCharsets.UTF_8);
    private final PtCollegeService ptCollegeService;

    public PtCollegeController(PtCollegeService ptCollegeService) {
        this.ptCollegeService = ptCollegeService;
    }

    @GetMapping("/tree")
    public ApiResponse<List<PtCollegeTreeItem>> treeCollege() {
        List<PtCollege> colleges = ptCollegeService.listCollege();
        List<PtCollegeTreeItem> treeItems =
                colleges.stream().map(PtCollegeTreeItem::new).collect(Collectors.toList());
        return ApiResponse.success(treeItems);
    }


    @GetMapping("/list")
    public ApiResponse<List<PtCollege>> listCollege() {
        return ApiResponse.success(ptCollegeService.listCollege());
    }

    //    @PreAuthorize("authentication.authorities.contains('null:insert')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Integer> uploadCollege(@RequestPart MultipartFile file) {
        Integer count = ptCollegeService.uploadCollege(file);
        return ApiResponse.success(count);
    }

    @GetMapping("{clgCode}")
    public ApiResponse<PtCollege> getCollege(@PathVariable String clgCode) {
        return ApiResponse.success(ptCollegeService.getCollege(clgCode));
    }

    @GetMapping("/template")
    public ResponseEntity<Resource> fileTemplate() {
        Resource resource = ptCollegeService.fileTemplate();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + FILE_TEMPLATE_NAME + "\"")
                .body(resource);
    }
}
