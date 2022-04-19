package com.rufeng.healthman.security.repositry;

import com.rufeng.healthman.common.util.JwtTokenUtils;
import com.rufeng.healthman.security.authentication.Authentication;
import com.rufeng.healthman.security.context.SecurityContext;
import com.rufeng.healthman.security.context.SecurityContextHolder;
import com.rufeng.healthman.service.RedisService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_HEADER_NAME;


/**
 * @author rufeng
 */
@Component
public class TokenSecurityContextRepository implements SecurityContextRepository {
    private final RedisService redisService;

    TokenSecurityContextRepository(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    @NonNull
    public SecurityContext loadContext(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        String token = request.getHeader(JWT_HEADER_NAME);
        if (token == null) {
            // TODO 可以记录
            return context;
        }
        String userId = JwtTokenUtils.getId(token);
        Authentication authentication = redisService.getObject(userId, Authentication.class);
        context.setAuthentication(authentication);
        return context;
    }

    /**
     * 完全存储在客户端，服务端空实现
     */
    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

    }
}