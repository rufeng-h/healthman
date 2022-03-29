package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtMeasurement;
import com.rufeng.healthman.pojo.data.PtMesurementFormdata;
import com.rufeng.healthman.service.PtMesurementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-29 23:51
 * @package com.rufeng.healthman.controller
 * @description TODO
 */
@Validated
@RequestMapping("/api/mesurement")
@SecurityRequirement(name = JWT_SCHEME_NAME)
@RestController
public class PtMesurementController {
    private final PtMesurementService ptMesurementService;

    public PtMesurementController(PtMesurementService ptMesurementService) {
        this.ptMesurementService = ptMesurementService;
    }

    @PostMapping
    public ApiResponse<PtMeasurement> addMesurement(@Validated @RequestBody PtMesurementFormdata formdata){
        return ApiResponse.success(ptMesurementService.addMesurement(formdata));

    }
}
