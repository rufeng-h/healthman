package com.rufeng.healthman.security;

import com.rufeng.healthman.security.context.SecurityContext;
import com.rufeng.healthman.security.context.SecurityContextHolder;
import com.rufeng.healthman.security.repositry.SecurityContextRepository;
import org.springframework.core.annotation.Order;
import org.springframework.core.log.LogMessage;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rufeng
 * @time 2022-04-19 17:31
 * @package com.rufeng.healthman.security
 * @description TODO
 */
@Order(1)
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    protected final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final SecurityContextRepository contextRepository;
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();
    private final Set<String> excludeUrlPatterns = new HashSet<>(16);

    private final Set<String> urlPatterns = new HashSet<>(16);

    public TokenAuthenticationFilter(SecurityContextRepository contextRepository) {
        this.contextRepository = contextRepository;
        addUrlPatterns("/api/**", "/test/api/**");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain) throws ServletException, IOException {
        SecurityContext contextBeforeChainExecution = this.contextRepository.loadContext(request);
        try {
            SecurityContextHolder.setContext(contextBeforeChainExecution);
            if (contextBeforeChainExecution.getAuthentication() == null) {
                logger.debug("Set SecurityContextHolder to empty SecurityContext");
            } else {
                if (this.logger.isDebugEnabled()) {
                    this.logger
                            .debug(LogMessage.format("Set SecurityContextHolder to %s", contextBeforeChainExecution));
                }
            }
            chain.doFilter(request, response);
        } finally {
            SecurityContext contextAfterChainExecution = SecurityContextHolder.getContext();
            // Crucial removal of SecurityContextHolder contents before anything else.
            SecurityContextHolder.clearContext();
            this.contextRepository.saveContext(contextAfterChainExecution, request, response);
            this.logger.debug("Cleared SecurityContextHolder to complete request");
        }
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        Assert.notNull(request, "Http servlet request must not be null");

        // check white list
        boolean result = excludeUrlPatterns.stream()
                .anyMatch(p -> antPathMatcher.match(p, urlPathHelper.getRequestUri(request)));

        return result || urlPatterns.stream()
                .noneMatch(p -> antPathMatcher.match(p, urlPathHelper.getRequestUri(request)));

    }

    public void addExcludeUrlPatterns(@NonNull String... excludeUrlPatterns) {
        Assert.notNull(excludeUrlPatterns, "Exclude url patterns must not be null");
        Collections.addAll(this.excludeUrlPatterns, excludeUrlPatterns);
    }

    public void addUrlPatterns(String... urlPatterns) {
        Assert.notNull(urlPatterns, "UrlPatterns must not be null");
        Collections.addAll(this.urlPatterns, urlPatterns);
    }

}
