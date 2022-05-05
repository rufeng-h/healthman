package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.ptdo.PtOperation;
import com.rufeng.healthman.security.authority.ApiAuthority;
import com.rufeng.healthman.security.authority.Authority;
import com.rufeng.healthman.service.PtOperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-05-01 21:07
 * @package com.rufeng.healthman.controller
 * @description 系统接口
 */
@RequestMapping("/api/oper")
@SecurityRequirement(name = JWT_SCHEME_NAME)
@RestController
@Validated
@ApiAuthority
@Tag(name = "Sys Oper", description = "系统接口")
public class PtOperationController {
    private final PtOperationService ptOperationService;

    public PtOperationController(PtOperationService ptOperationService) {
        this.ptOperationService = ptOperationService;
    }

    @Operation(operationId = Authority.PtOperation.OPERATION_LIST, summary = "接口列表")
    @GetMapping("/list")
    public ApiResponse<List<PtOperation>> listOper() {
        return ApiResponse.success(ptOperationService.list());
    }

    @GetMapping
    public ApiResponse<ApiPage<PtOperation>> pageOper(@RequestParam(defaultValue = "1") @Min(1) Integer page,
                                                      @RequestParam(defaultValue = "10") @Min(1) Integer pageSize) {
        return ApiResponse.success(ptOperationService.page(page, pageSize));
    }
}
