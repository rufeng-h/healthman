package com.rufeng.healthman.controller;

import com.rufeng.healthman.common.ApiResponse;
import com.rufeng.healthman.common.JwtTokenUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    @GetMapping("/api/getUserInfo")
    public ApiResponse<Map<String, Object>> getUserInfo() {
        HashMap<String, Object> map = new HashMap<>(10);
        HashMap<String, String> roleMap = new HashMap<>(2);
        roleMap.put("roleName", "管理员");
        roleMap.put("value", "admin");
        map.put("roles", Collections.singletonList(roleMap));
        map.put("userId", 100102);
        map.put("username", "rufeng");
        map.put("realName", "chunfengh");
        map.put("avatar", "https://p1.music.126.net/Y3C5ob6SQjXRijaVNBu4Sw==/109951164400648086.jpg");
        map.put("desc", "兴趣所致");
        return ApiResponse.success(map);
    }

    @GetMapping("/api/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.success();
    }

    @GetMapping("/login")
    public ApiResponse<Map<String, String>> login() {
        String token = JwtTokenUtil.generateToken("vben", 123456);
        return ApiResponse.success(Collections.singletonMap("token", token));
    }
}
