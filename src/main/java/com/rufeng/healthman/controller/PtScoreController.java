package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DTO.ptscore.StuScoreInfo;
import com.rufeng.healthman.pojo.Query.StuScoreQuery;
import com.rufeng.healthman.service.PtScoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping
    public ApiResponse<List<StuScoreInfo>> listScore(@Validated StuScoreQuery query) {
        return ApiResponse.success(ptScoreService.listScore(query));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Integer> uploadScore(@RequestPart MultipartFile file, @RequestParam(required = false) Long msId) {
        return ApiResponse.success(ptScoreService.uploadScore(file, msId));
    }
}
