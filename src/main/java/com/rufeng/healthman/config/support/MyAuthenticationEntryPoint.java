package com.rufeng.healthman.config.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rufeng.healthman.common.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author rufeng
 */
@Component
@Slf4j
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    public MyAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        log.debug(authException.getMessage());
        response.getWriter().println(objectMapper.writeValueAsString(
                ApiResponse.authenticateFailed()));
        response.getWriter().flush();
    }
}