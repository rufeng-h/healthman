package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementScoreInfo;
import com.rufeng.healthman.pojo.dto.ptscore.ScoreInfo;
import com.rufeng.healthman.pojo.query.PtScoreQuery;
import com.rufeng.healthman.service.PtScoreService;
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

/**
 * @author rufeng
 * @time 2022-03-18 0:21
 * @package com.rufeng.healthman.controller
 * @description 成绩查询
 */
@RestController
@RequestMapping("/api/score")
@Validated
@Tag(name = "Score Api", description = "成绩操作接口")
public class PtScoreController {
    private final PtScoreService ptScoreService;

    public PtScoreController(PtScoreService ptScoreService) {
        this.ptScoreService = ptScoreService;
    }

    @GetMapping("/ms")
    public ApiResponse<ApiPage<MeasurementScoreInfo>> pageScore(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer pageSize,
            @Validated(PtScoreQuery.MsQuery.class) PtScoreQuery query) {
        return ApiResponse.success(ptScoreService.pageMsScore(page, pageSize, query));
    }

    @GetMapping("/stu")
    public ApiResponse<ApiPage<ScoreInfo>> pageStuScore(@RequestParam(defaultValue = "1") @Min(1) Integer page,
                                                        @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer pageSize,
                                                        @Validated(PtScoreQuery.StuQuery.class) PtScoreQuery query) {
        return ApiResponse.success(ptScoreService.pageStuScore(page, pageSize, query));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Integer> uploadScore(@RequestPart MultipartFile file, @RequestParam Long msId) {
        return ApiResponse.success(ptScoreService.uploadScore(file, msId));
    }

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
