package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DO.PtCollege;
import com.rufeng.healthman.pojo.DTO.ptcollege.PtCollegeInfo;
import com.rufeng.healthman.pojo.DTO.ptcollege.PtCollegeTreeItem;
import com.rufeng.healthman.pojo.Query.PtCollegeQuery;
import com.rufeng.healthman.service.PtCollegeService;
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
 * @time 2022-03-12 9:53
 * @package com.rufeng.healthman.controller
 * @description TODO
 */
@Validated
@RestController
@Tag(name = "College Api", description = "学院操作接口")
@SecurityRequirement(name = JWT_SCHEME_NAME)
@RequestMapping("/api/college")
public class PtCollegeController {
    private final PtCollegeService ptCollegeService;

    public PtCollegeController(PtCollegeService ptCollegeService) {
        this.ptCollegeService = ptCollegeService;
    }

    @GetMapping("/tree")
    public ApiResponse<List<PtCollegeTreeItem>> treeCollege() {
        List<PtCollege> colleges = ptCollegeService.listCollege();
        List<PtCollegeTreeItem> treeItems =
                colleges.stream().map(PtCollegeTreeItem::new).collect(Collectors.toList());
        return ApiResponse.success(treeItems);
    }

    @GetMapping("/list")
    public ApiResponse<List<PtCollege>> listCollege() {
        return ApiResponse.success(ptCollegeService.listCollege());
    }

    @GetMapping("/info")
    public ApiResponse<PtCollegeInfo> collegeInfo(@RequestParam Long id) {
        return ApiResponse.success(ptCollegeService.getCollegeInfo(id));
    }
}
