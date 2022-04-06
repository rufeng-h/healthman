package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtMeasurement;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.MeasurementDetail;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.MeasurementInfo;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.StuMeasurementDetail;
import com.rufeng.healthman.pojo.Query.PtMeasurementQuery;
import com.rufeng.healthman.pojo.data.PtMeasurementFormdata;
import com.rufeng.healthman.service.PtMesurementService;
import com.rufeng.healthman.validation.group.Insert;
import com.rufeng.healthman.validation.group.Update;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-29 23:51
 * @package com.rufeng.healthman.controller
 * @description 测量记录
 */
@Validated
@RequestMapping("/api/measurement")
@SecurityRequirement(name = JWT_SCHEME_NAME)
@RestController
public class PtMesurementController {
    private final PtMesurementService ptMesurementService;

    public PtMesurementController(PtMesurementService ptMesurementService) {
        this.ptMesurementService = ptMesurementService;
    }

    @PostMapping
    public ApiResponse<PtMeasurement> addMesurement(@Validated(Insert.class) @RequestBody PtMeasurementFormdata formdata) {
        return ApiResponse.success(ptMesurementService.addMesurement(formdata));
    }

    @GetMapping
    public ApiResponse<ApiPage<MeasurementInfo>> pageMeasurement(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "6") @Min(1) @Max(100) Integer pageSize,
            @Validated PtMeasurementQuery query) {
        return ApiResponse.success(ptMesurementService.pageMeasurementInfo(page, pageSize, query));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ApiResponse<Boolean> deleteMeasurement(@RequestParam Long msId) {
        return ApiResponse.success(ptMesurementService.deleteById(msId));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ApiResponse<Boolean> updateMeasurement(
            @RequestBody @Validated(Update.class) PtMeasurementFormdata formdata) {
        return ApiResponse.success(ptMesurementService.updateMeasurement(formdata));
    }

    @GetMapping("/{msId}")
    public ApiResponse<MeasurementDetail> getMeasurementDetail(@PathVariable Long msId) {
        return ApiResponse.success(ptMesurementService.getMeasurementDetail(msId));
    }

    @GetMapping("/stu/{stuId}")
    public ApiResponse<ApiPage<StuMeasurementDetail>> pageStuMsDetail(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "6") @Min(1) @Max(100) Integer pageSize,
            @PathVariable String stuId) {
        return ApiResponse.success(ptMesurementService.pageStuMsDetail(page, pageSize, stuId));
    }
}
