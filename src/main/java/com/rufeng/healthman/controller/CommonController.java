package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.pojo.DTO.ptuser.LoginResult;
import com.rufeng.healthman.pojo.Query.LoginQuery;
import com.rufeng.healthman.service.PtUserService;
import com.rufeng.healthman.service.RedisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.rufeng.healthman.config.RedisConfig.REDIS_KEY_PREFIX;

/**
 * @author rufeng
 * @time 2022-03-05 22:43
 * @package com.rufeng.healthman.controller
 * @description .
 */
@RestController
@Validated
@Tag(name = "common", description = "common")
public class CommonController {
    private final PtUserService userService;
    private final RedisService redisService;

    public CommonController(PtUserService userService, RedisService redisService) {
        this.userService = userService;
        this.redisService = redisService;
    }

    @GetMapping("/api/logout")
    public ApiResponse<Void> logout() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        redisService.remove(REDIS_KEY_PREFIX + ":" + userId);
        return ApiResponse.success();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResult> login(
            @RequestBody @Validated LoginQuery loginQuery) {
        return ApiResponse.success(userService.login(loginQuery));
    }
}
