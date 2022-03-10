package com.rufeng.healthman.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.SecurityContextRepository;


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
}
