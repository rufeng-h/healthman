package com.rufeng.healthman.controller.test;

import com.rufeng.healthman.common.ApiPage;
import com.rufeng.healthman.common.ApiResponse;
import com.rufeng.healthman.common.JwtTokenUtil;
import com.rufeng.healthman.domain.User;
import com.rufeng.healthman.exceptions.test.TestException;
import com.rufeng.healthman.service.test.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

    @Operation(description = "所有用户")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/api/all")
    public List<Map<String, Object>> getAll() {
        return testService.getAll();
    }

    @GetMapping("/page")
    @Operation(description = "分页查询", summary = "分页")
    public ApiResponse<ApiPage<User>> testPage(
            @Parameter(description = "当前页数") @RequestParam(defaultValue = "1") @Range(min = 1) Integer pageNum,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "5") Integer pageSize) {
        return ApiResponse.success(testService.selectPage(pageNum, pageSize));
    }

    @GetMapping("/error")
    @Operation(description = "测试异常处理")
    public ApiResponse<Void> testError() {
        throw new TestException("测试异常");
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(@RequestBody User user) {
        return ApiResponse.success(Collections.singletonMap("token", JwtTokenUtil.generateToken(user)));
    }

}
