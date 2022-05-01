package com.rufeng.healthman.controller;

import com.rufeng.healthman.pojo.ptdo.PtOperation;
import com.rufeng.healthman.security.authority.ApiAuthority;
import com.rufeng.healthman.security.authority.Authority;
import com.rufeng.healthman.service.PtOperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@ApiAuthority
public class PtOperationController {
    private final PtOperationService ptOperationService;

    public PtOperationController(PtOperationService ptOperationService) {
        this.ptOperationService = ptOperationService;
    }

    @Operation(operationId = Authority.PtOperation.OPERATION_LIST, summary = "接口列表")
    @GetMapping
    public List<PtOperation> listOper() {
        return ptOperationService.list();
    }
}
