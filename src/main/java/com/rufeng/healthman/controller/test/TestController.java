package com.rufeng.healthman.controller.test;

import com.rufeng.healthman.common.ApiPage;
import com.rufeng.healthman.common.ApiResponse;
import com.rufeng.healthman.domain.User;
import com.rufeng.healthman.service.test.TestService;
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
 * @description TODO
 */
@RestController
@Validated
@RequestMapping("/test")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getAll() {
        return testService.getAll();
    }

    @GetMapping("/page")
    public ApiResponse<ApiPage<User>> testPage(
            @RequestParam(defaultValue = "1") @Range(min = 1) Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        return ApiResponse.success(testService.selectPage(pageNum, pageSize));
    }
}
