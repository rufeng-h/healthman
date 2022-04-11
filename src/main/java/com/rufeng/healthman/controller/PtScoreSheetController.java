package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.ptdo.PtScoreSheet;
import com.rufeng.healthman.pojo.query.PtScoreSheetQuery;
import com.rufeng.healthman.service.PtScoreSheetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author rufeng
 * @time 2022-03-17 18:04
 * @package com.rufeng.healthman.controller
 * @description 评分量表
 */
@Validated
@RestController
@RequestMapping("/scoreSheet")
@Tag(name = "Score Sheet Api", description = "评分量表")
public class PtScoreSheetController {
    private final PtScoreSheetService ptScoreSheetService;

    public PtScoreSheetController(PtScoreSheetService ptScoreSheetService) {
        this.ptScoreSheetService = ptScoreSheetService;
    }

    @GetMapping
    public ApiResponse<ApiPage<PtScoreSheet>> pageScoreSheet(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @Validated PtScoreSheetQuery query) {
        return ApiResponse.success(ptScoreSheetService.pageScoreSheet(page, pageSize, query));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Integer> uploadScoreSheet(@RequestPart MultipartFile file, @RequestParam Long subId) {
        return ApiResponse.success(ptScoreSheetService.uploadScoreSheet(subId, file));
    }
}
