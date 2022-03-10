package com.rufeng.healthman.config.support;

import com.rufeng.healthman.common.JwtTokenUtil;
import com.rufeng.healthman.service.RedisService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rufeng.healthman.config.RedisConfig.REDIS_KEY_PREFIX;
import static com.rufeng.healthman.config.SecurityConfig.JWT_HEADER_NAME;

/**
 * @author rufeng
 */
@Component
public class TokenSecurityContextRepository implements SecurityContextRepository {
    private final Log logger = LogFactory.getLog(TokenSecurityContextRepository.class);
    private final RedisService redisService;

    TokenSecurityContextRepository(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        HttpServletRequest request = requestResponseHolder.getRequest();
        String token = request.getHeader(JWT_HEADER_NAME);
        if (token == null) {
            // TODO 可以记录
            logger.debug("未携带token访问!");
            return context;
        }
        Long userId = JwtTokenUtil.getId(token);
        Authentication authentication = redisService.getObject(REDIS_KEY_PREFIX + ":" + userId, Authentication.class);
        context.setAuthentication(authentication);
        logger.debug(authentication.getPrincipal() + "认证成功!");
        return context;
    }

    /**
     * 完全存储在客户端，服务端空实现
     */
    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 虽然没有用到，但还是实现一下
     */
    @Override
    public boolean containsContext(HttpServletRequest request) {
        String token = request.getHeader(JWT_HEADER_NAME);
        return redisService.hasKey(REDIS_KEY_PREFIX + ":" + JwtTokenUtil.getId(token));
    }
}