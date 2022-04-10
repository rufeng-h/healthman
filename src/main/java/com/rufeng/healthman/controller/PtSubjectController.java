package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.ptdo.PtSubject;
import com.rufeng.healthman.pojo.dto.ptsubject.SubjectInfo;
import com.rufeng.healthman.pojo.data.PtSubjectFormdata;
import com.rufeng.healthman.pojo.query.PtSubjectQuery;
import com.rufeng.healthman.service.PtSubjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author rufeng
 * @time 2022-03-17 18:40
 * @package com.rufeng.healthman.controller
 * @description TODO
 */
@RequestMapping("/api/subject")
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
    public ApiResponse<PtSubject> addSubject(@RequestBody PtSubjectFormdata data) {
        return ApiResponse.success(ptSubjectService.addSubject(data));
    }

    @GetMapping
    public ApiResponse<ApiPage<SubjectInfo>> pageSubInfo(@RequestParam(defaultValue = "1") @Min(1) Integer page,
                                                         @RequestParam(defaultValue = "8") @Min(1) @Max(100) Integer pageSize,
                                                         @Validated PtSubjectQuery query) {
        return ApiResponse.success(ptSubjectService.pageSubjectInfo(page, pageSize, query));
    }
}
