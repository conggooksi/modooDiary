package com.secondWind.modooDiary.api.member.auth.domain.spec;

import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.exception.code.AuthErrorCode;
import com.secondWind.modooDiary.common.spec.AbstractSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailSpecification extends AbstractSpecification<String> {
    @Override
    public boolean isSatisfiedBy(String email) {
        // 소문자, 대문자, 숫자, 특수문자 포함, 8자 이상 16자 이하
        // \\w+@\\w+\\.\\w+(\\.\\w+)?
        return Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email);
    }

    @Override
    public void check(String email) throws ApiException {
        if (!isSatisfiedBy(email)) {
            throw ApiException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .errorCode(AuthErrorCode.EMAIL_NOT_ENOUGH_CONDITION.getCode())
                    .errorMessage(AuthErrorCode.EMAIL_NOT_ENOUGH_CONDITION.getMessage())
                    .build();
        }
    }
}
