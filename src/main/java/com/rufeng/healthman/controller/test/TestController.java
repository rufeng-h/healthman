package com.rufeng.healthman.controller.test;

import com.rufeng.healthman.common.ApiPage;
import com.rufeng.healthman.common.ApiResponse;
import com.rufeng.healthman.domain.User;
import com.rufeng.healthman.service.test.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author rufeng
 * @time 2022-01-10 15:24
 * @package com.rufeng.healthman.controller.test
 * @description 测试接口
 */
@Tag(name = "test", description = "测试接口")
@RestController
@Validated
@RequestMapping("/test")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @Operation()
    @GetMapping("/all")
    public List<Map<String, Object>> getAll() {
        return testService.getAll();
    }

    @GetMapping("/page")
    @Operation(description = "分页查询", summary = "分页")
    public ApiResponse<ApiPage<User>> testPage(
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "1") @Range(min = 1) Integer pageNum,
            @Parameter(description = "当前页数") @RequestParam(defaultValue = "5") Integer pageSize) {
        return ApiResponse.success(testService.selectPage(pageNum, pageSize));
    }
}
