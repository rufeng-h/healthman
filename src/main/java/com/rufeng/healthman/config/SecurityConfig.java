package com.rufeng.healthman.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rufeng.healthman.common.ApiResponse;
import com.rufeng.healthman.common.JwtTokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;


/**
 * SpringSecurity的配置
 *
 * @author rufeng
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String JWT_HEADER_NAME = "Authorization";
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final SecurityContextRepository contextRepository;

    public SecurityConfig(AccessDeniedHandler accessDeniedHandler,
                          AuthenticationEntryPoint authenticationEntryPoint,
                          SecurityContextRepository contextRepository) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.contextRepository = contextRepository;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityContext().securityContextRepository(contextRepository);

        httpSecurity.csrf().disable();
        /* 使用HttpServlet，没有包装需求 */
        httpSecurity.servletApi().disable();
        /*登入登出在Controller中处理*/
        httpSecurity.logout().disable();
        /* 请求头不需要额外操作 */
        httpSecurity.headers().disable();
        /*重定向认证后重新处理请求*/
        httpSecurity.requestCache().disable();
        httpSecurity.sessionManagement().disable();
        /* 不配置自动禁用 */
        /*httpSecurity.formLogin();*/
        /*httpSecurity.rememberMe();*/
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
        httpSecurity.authorizeRequests()
                .antMatchers("/api/**", "/test/api/**").authenticated()
                .antMatchers(HttpMethod.OPTIONS).permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Component
    public static class MyAccessDeniedHandler implements AccessDeniedHandler {
        private final ObjectMapper objectMapper;
        private final Log logger = LogFactory.getLog(MyAccessDeniedHandler.class);

        public MyAccessDeniedHandler(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
            response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            logger.debug(accessDeniedException.getMessage());
            response.getWriter().println(objectMapper.writeValueAsString(
                    ApiResponse.unAuthorized()));
            response.getWriter().flush();
        }
    }

    @Component
    static class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

        private final ObjectMapper objectMapper;
        private final Log logger = LogFactory.getLog(MyAuthenticationEntryPoint.class);

        public MyAuthenticationEntryPoint(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            logger.debug(authException.getMessage());
            response.getWriter().println(objectMapper.writeValueAsString(
                    ApiResponse.authenticateFailed()));
            response.getWriter().flush();
        }
    }

    @Component
    static class TokenSecurityContextRepository implements SecurityContextRepository {

        @Override
        public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            HttpServletRequest request = requestResponseHolder.getRequest();
            String token = request.getHeader(JWT_HEADER_NAME);
            if (token != null) {
                context.setAuthentication(
                        new UsernamePasswordAuthenticationToken(JwtTokenUtil.getSubject(token), "token", Collections.emptyList()));
            }
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
            return token != null && JwtTokenUtil.isValid(token);
        }
    }
}
