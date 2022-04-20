package com.rufeng.healthman.security.handler;

import com.rufeng.healthman.exceptions.AuthenticationException;
import org.springframework.lang.NonNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rufeng
 * @time 2022-04-20 0:14
 * @package com.rufeng.healthman.security.handler
 * @description TODO
 */
public interface AuthenticateFailHandler {
    /**
     * 认证失败
     *
     * @param request
     * @param response
     * @param ex
     * @throws ServletException
     * @throws IOException
     */
    void onAuthenticateFail(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AuthenticationException ex) throws IOException, ServletException;
}
