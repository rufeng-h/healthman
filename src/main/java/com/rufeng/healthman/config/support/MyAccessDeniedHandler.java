package com.rufeng.healthman.config.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rufeng.healthman.common.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    public MyAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        log.debug(accessDeniedException.getMessage());
        response.getWriter().println(objectMapper.writeValueAsString(
                ApiResponse.accessDenied()));
        response.getWriter().flush();
    }
}