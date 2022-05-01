package com.rufeng.healthman.security.repositry;

import com.rufeng.healthman.common.util.JwtTokenUtils;
import com.rufeng.healthman.enums.UserTypeEnum;
import com.rufeng.healthman.security.authentication.Authentication;
import com.rufeng.healthman.security.context.SecurityContext;
import com.rufeng.healthman.security.context.SecurityContextHolder;
import com.rufeng.healthman.security.support.UserInfo;
import com.rufeng.healthman.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rufeng.healthman.config.OpenApiConfig.JWT_HEADER_NAME;


/**
 * @author rufeng
 */
@Component
@Slf4j
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
            log.debug("未认证用户！");
            return context;
        }
        String userId = JwtTokenUtils.getId(token);
        UserTypeEnum userType = JwtTokenUtils.getSubject(token);
        Authentication authentication = redisService.getObject(UserInfo.userKey(userType, userId), Authentication.class);
        if (authentication == null) {
            log.warn("空的认证信息！");
        } else {
            UserInfo userInfo = authentication.getUserInfo();
            log.debug(userInfo.getUserType() + ": " + userInfo.getUsername());
        }
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