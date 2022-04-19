package com.rufeng.healthman.security.repositry;

import com.rufeng.healthman.security.context.SecurityContext;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author rufeng
 */
public interface SecurityContextRepository {
    /**
     * load context.
     * if no context return empty context
     *
     * @param request
     * @return never null
     */
    @NonNull
    SecurityContext loadContext(HttpServletRequest request);

    /**
     * Stores the security context on completion of a request.
     *
     * @param context  the non-null context which was obtained from the holder.
     * @param request
     * @param response
     */
    void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response);

}
