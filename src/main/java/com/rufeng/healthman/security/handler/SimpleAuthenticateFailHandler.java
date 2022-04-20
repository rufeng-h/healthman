package com.rufeng.healthman.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rufeng.healthman.common.api.ApiResponse;
import com.rufeng.healthman.exceptions.AuthenticationException;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author rufeng
 * @time 2022-04-20 0:15
 * @package com.rufeng.healthman.security.handler
 * @description TODO
 */
@Component
public class SimpleAuthenticateFailHandler implements AuthenticateFailHandler {
    private final ObjectMapper objectMapper;

    public SimpleAuthenticateFailHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticateFail(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AuthenticationException ex) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        response.getWriter().println(objectMapper.writeValueAsString(ApiResponse.authenticateFailed(ex.getMessage())));
        response.getWriter().flush();
    }
}
