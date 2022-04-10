package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.ptdo.PtSubgroup;
import com.rufeng.healthman.pojo.dto.subgroup.SubGroupInfo;
import com.rufeng.healthman.pojo.query.PtSubgroupQuery;
import com.rufeng.healthman.pojo.data.PtSubGroupFormdata;
import com.rufeng.healthman.service.PtSubgroupService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-27 19:30
 * @package com.rufeng.healthman.controller
 * @description TODO
 */
@RestController
@Validated
@RequestMapping("/api/subGroup")
@SecurityRequirement(name = JWT_SCHEME_NAME)
public class PtSubGroupController {
    private final PtSubgroupService ptSubgroupService;

    public PtSubGroupController(PtSubgroupService ptSubgroupService) {
        this.ptSubgroupService = ptSubgroupService;
    }

    @GetMapping("/list")
    public ApiResponse<List<PtSubgroup>> listSubGroup() {
        return ApiResponse.success(ptSubgroupService.listSubGroup());
    }

    @PostMapping
    public ApiResponse<PtSubgroup> addSubGroup(@Validated @RequestBody PtSubGroupFormdata formdata) {
        return ApiResponse.success(ptSubgroupService.addSubGroup(formdata));
    }

    @GetMapping
    public ApiResponse<ApiPage<SubGroupInfo>> pageSubGroupInfo(@RequestParam(defaultValue = "1") @Min(1) Integer page,
                                                               @RequestParam(defaultValue = "3") @Min(1) @Max(100) Integer pageSize,
                                                               @Validated PtSubgroupQuery query) {
        return ApiResponse.success(ptSubgroupService.pageSubGroupInfo(page, pageSize, query));
    }

    @RequestMapping(value = "/{grpId}", method = RequestMethod.DELETE)
    public ApiResponse<Boolean> deleteSubGrp(@PathVariable Long grpId) {
        return ApiResponse.success(ptSubgroupService.deleteGrp(grpId));
    }
}
