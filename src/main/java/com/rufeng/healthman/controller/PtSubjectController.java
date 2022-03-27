package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtSubject;
import com.rufeng.healthman.pojo.data.PtScoreSheetFormdata;
import com.rufeng.healthman.service.PtSubjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-17 18:40
 * @package com.rufeng.healthman.controller
 * @description TODO
 */
@RequestMapping("/subject")
@Validated
@RestController
public class PtSubjectController {
    private final PtSubjectService ptSubjectService;

    public PtSubjectController(PtSubjectService ptSubjectService) {
        this.ptSubjectService = ptSubjectService;
    }

    @GetMapping("/list")
    public ApiResponse<List<PtSubject>> listSubject() {
        return ApiResponse.success(ptSubjectService.listSubject());
    }

    @PostMapping
    public ApiResponse<PtSubject> addSubject(@RequestBody PtScoreSheetFormdata data) {
        return ApiResponse.success(ptSubjectService.addSubject(data));
    }
}
