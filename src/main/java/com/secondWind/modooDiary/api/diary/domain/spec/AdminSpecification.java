package com.secondWind.modooDiary.api.diary.domain.spec;

import com.secondWind.modooDiary.common.enumerate.Authority;
import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.exception.code.AuthErrorCode;
import com.secondWind.modooDiary.common.spec.AbstractSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AdminSpecification extends AbstractSpecification<Authentication> {
    @Override
    public boolean isSatisfiedBy(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Authority.ROLE_ADMIN.toString()));
    }

    @Override
    public void check(Authentication authentication) throws ApiException {
        if (!isSatisfiedBy(authentication)) {
            throw ApiException.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .errorMessage(AuthErrorCode.PERMISSION_DENIED.getMessage())
                    .errorCode(AuthErrorCode.PERMISSION_DENIED.getCode())
                    .build();
        }
    }
}
