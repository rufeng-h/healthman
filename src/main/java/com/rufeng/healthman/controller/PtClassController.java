package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiPage;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtClass;
import com.rufeng.healthman.pojo.DTO.ptclass.PtClassTreeItem;
import com.rufeng.healthman.pojo.Query.PtClassQuery;
import com.rufeng.healthman.service.PtClassService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_SCHEME_NAME;

/**
 * @author rufeng
 * @time 2022-03-06 22:17
 * @package com.rufeng.healthman.controller
 * @description 班级接口
 */
@RestController
@Validated
@RequestMapping("/api/class")
@SecurityRequirement(name = JWT_SCHEME_NAME)
@Tag(name = "PtClass Operation", description = "PtClass")
public class PtClassController {
    private final PtClassService ptClassService;

    public PtClassController(PtClassService ptClassService) {
        this.ptClassService = ptClassService;
    }

    @GetMapping
    public ApiResponse<ApiPage<PtClass>> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @Validated PtClassQuery ptClassQuery) {
        return ApiResponse.success(ptClassService.pageClass(page, pageSize, ptClassQuery));
    }

    @GetMapping("/tree")
    public ApiResponse<List<PtClassTreeItem>> tree(@Validated PtClassQuery query) {
        List<PtClass> classList = ptClassService.listClass(query);
        List<PtClassTreeItem> items = classList.stream().map(PtClassTreeItem::new).collect(Collectors.toList());
        return ApiResponse.success(items);
    }

    @GetMapping("/list")
    public ApiResponse<List<PtClass>> list(@Validated PtClassQuery query) {
        return ApiResponse.success(ptClassService.listClass(query));
    }
}
