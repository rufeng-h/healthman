package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtMeasurement;
import com.rufeng.healthman.pojo.DTO.ptmeasurement.MeasurementInfo;
import com.rufeng.healthman.pojo.Query.PtMeasurementQuery;
import com.rufeng.healthman.pojo.data.PtMesurementFormdata;
import com.rufeng.healthman.service.PtMesurementService;
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
    public ApiResponse<PtMeasurement> addMesurement(@Validated @RequestBody PtMesurementFormdata formdata) {
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
}
