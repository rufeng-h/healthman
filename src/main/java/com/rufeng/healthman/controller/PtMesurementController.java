package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.data.PtMeasurementFormdata;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementDetail;
import com.rufeng.healthman.pojo.dto.ptmeasurement.MeasurementInfo;
import com.rufeng.healthman.pojo.dto.ptmeasurement.StuMeasurementDetail;
import com.rufeng.healthman.pojo.ptdo.PtMeasurement;
import com.rufeng.healthman.pojo.query.PtMeasurementQuery;
import com.rufeng.healthman.security.authority.ApiAuthority;
import com.rufeng.healthman.security.authority.Authority;
import com.rufeng.healthman.service.PtMesurementService;
import com.rufeng.healthman.validation.group.Insert;
import com.rufeng.healthman.validation.group.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
@Tag(name = "Ms Api", description = "体测操作")
@ApiAuthority
public class PtMesurementController {
    private final PtMesurementService ptMesurementService;

    public PtMesurementController(PtMesurementService ptMesurementService) {
        this.ptMesurementService = ptMesurementService;
    }

    @Operation(operationId = Authority.MS_INSERT, summary = "新建体测")
    @PostMapping
    public ApiResponse<PtMeasurement> addMesurement(
            @Validated(Insert.class) @RequestBody PtMeasurementFormdata formdata) {
        return ApiResponse.success(ptMesurementService.addMesurement(formdata));
    }

    @Operation(operationId = Authority.MS_PAGE, summary = "体测列表")
    @GetMapping
    public ApiResponse<ApiPage<MeasurementInfo>> pageMeasurement(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "6") @Min(1) @Max(100) Integer pageSize,
            @Validated PtMeasurementQuery query) {
        return ApiResponse.success(ptMesurementService.pageMeasurementInfo(page, pageSize, query));
    }

    @Operation(operationId = Authority.MS_DELETE, summary = "删除体测")
    @DeleteMapping("/{msId}")
    public ApiResponse<Boolean> deleteMeasurement(@PathVariable Long msId) {
        return ApiResponse.success(ptMesurementService.deleteById(msId));
    }

    @Operation(operationId = Authority.MS_UPDATE, summary = "修改体测信息")
    @PutMapping
    public ApiResponse<Boolean> updateMeasurement(
            @RequestBody @Validated(Update.class) PtMeasurementFormdata formdata) {
        return ApiResponse.success(ptMesurementService.updateMeasurement(formdata));
    }

    @Operation(operationId = Authority.MS_DETAIL, summary = "体测结果数据")
    @GetMapping("/{msId}")
    public ApiResponse<MeasurementDetail> getMeasurementDetail(@PathVariable Long msId) {
        return ApiResponse.success(ptMesurementService.getMeasurementDetail(msId));
    }

    @Operation(operationId = Authority.MS_STUDETAIL, summary = "学生个人成绩")
    @GetMapping("/stu/{stuId}")
    public ApiResponse<ApiPage<StuMeasurementDetail>> pageStuMsDetail(
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @RequestParam(defaultValue = "6") @Min(1) @Max(100) Integer pageSize,
            @PathVariable String stuId) {
        return ApiResponse.success(ptMesurementService.pageStuMsDetail(page, pageSize, stuId));
    }

    @Operation(operationId = Authority.MS_TEMPLATE, summary = "体测成绩模板")
    @GetMapping("/template/{msId}")
    public ResponseEntity<Resource> excelTemplate(@PathVariable Long msId) {
        PtMeasurement measurement = ptMesurementService.getMeasurement(msId);
        Resource resource = ptMesurementService.excelTemplate(msId);
        String filename = URLEncoder.encode(measurement.getMsName() + ".xlsx", StandardCharsets.UTF_8);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}
