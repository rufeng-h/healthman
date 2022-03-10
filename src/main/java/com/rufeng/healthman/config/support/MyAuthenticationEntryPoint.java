package com.rufeng.healthman.config.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rufeng.healthman.common.api.ApiResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

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