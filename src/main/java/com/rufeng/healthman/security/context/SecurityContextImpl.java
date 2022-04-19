package com.rufeng.healthman.security.context;

import com.rufeng.healthman.security.authentication.Authentication;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

/**
 * Security context implementation.
 *
 * @author johnniang
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SecurityContextImpl implements SecurityContext {

    private Authentication authentication;

    @Override
    @Nullable
    public Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

}
