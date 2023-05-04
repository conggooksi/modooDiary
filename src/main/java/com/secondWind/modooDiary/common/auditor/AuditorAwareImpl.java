package com.secondWind.modooDiary.common.auditor;

import com.secondWind.modooDiary.common.enumerate.Authority;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    Collection<? extends GrantedAuthority> auth = authentication.getAuthorities();
                    boolean isUser = auth.contains(new SimpleGrantedAuthority((Authority.ROLE_USER.toString())));
                    boolean isAdmin = auth.contains(new SimpleGrantedAuthority(Authority.ROLE_ADMIN.toString()));
                    if (isUser || isAdmin) return Long.parseLong(authentication.getName());
                    return null;
                });
    }
}
