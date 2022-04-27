package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementScoreInfo;
import com.rufeng.healthman.pojo.dto.ptscore.PtScoreInfo;
import com.rufeng.healthman.pojo.query.PtScoreQuery;
import com.rufeng.healthman.security.authority.ApiAuthority;
import com.rufeng.healthman.security.authority.Authority;
import com.rufeng.healthman.service.PtScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-18 0:21
 * @package com.rufeng.healthman.controller
 * @description 成绩操作接口
 */
@RestController
@RequestMapping("/api/score")
@Validated
@Tag(name = "Score Api", description = "成绩操作接口")
@SecurityRequirement(name = JWT_SCHEME_NAME)
@ApiAuthority
public class PtScoreController {
    private final PtScoreService ptScoreService;

    public PtScoreController(PtScoreService ptScoreService) {
        this.ptScoreService = ptScoreService;
    }

    @Operation(operationId = Authority.PtScore.SCORE_PAGE, summary = "体测成绩列表")
    @GetMapping("/ms")
    public ApiResponse<ApiPage<MeasurementScoreInfo>> pageScore(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer pageSize,
            @Validated(PtScoreQuery.MsQuery.class) PtScoreQuery query) {
        return ApiResponse.success(ptScoreService.pageMsScore(page, pageSize, query));
    }

    @Operation(operationId = Authority.PtScore.SCORE_STU, summary = "学生历次体测成绩")
    @GetMapping("/stu")
    public ApiResponse<ApiPage<PtScoreInfo>> pageStuScore(@RequestParam(defaultValue = "1") @Min(1) Integer page,
                                                          @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer pageSize,
                                                          @Validated(PtScoreQuery.StuQuery.class) PtScoreQuery query) {
        return ApiResponse.success(ptScoreService.pageStuScore(page, pageSize, query));
    }

    @Operation(operationId = Authority.PtScore.SCORE_UPLOAD, summary = "体测成绩上传")
    @PostMapping(value = "/upload/{msId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Integer> uploadScore(@RequestPart MultipartFile file, @PathVariable Long msId) {
        return ApiResponse.success(ptScoreService.uploadScore(file, msId));
    }

    @Operation(operationId = Authority.PtScore.SCORE_DOWNLOAD, summary = "测验成绩下载")
    @GetMapping("/download")
    public ResponseEntity<Resource> download(@Validated PtScoreQuery query) {
        Resource resource = ptScoreService.downloadScore(query);
        String filename = DigestUtils.md5DigestAsHex(new Date().toString().getBytes(StandardCharsets.UTF_8)) + ".xlsx";
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}
