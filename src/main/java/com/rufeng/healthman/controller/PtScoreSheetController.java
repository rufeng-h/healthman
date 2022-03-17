package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtScoreSheet;
import com.rufeng.healthman.pojo.Query.PtScoreSheetQuery;
import com.rufeng.healthman.service.PtScoreSheetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-17 18:04
 * @package com.rufeng.healthman.controller
 * @description TODO
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

    @GetMapping("/list")
    public ApiResponse<List<PtScoreSheet>> listScoreSheet(@Validated PtScoreSheetQuery query) {
        return ApiResponse.success(ptScoreSheetService.listScoreSheet(query));
    }
}
