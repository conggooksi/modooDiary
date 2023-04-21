package com.secondWind.modooDiary.api.member.auth.domain.spec;

import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.exception.code.AuthErrorCode;
import com.secondWind.modooDiary.common.spec.AbstractSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PasswordSpecification extends AbstractSpecification<String> {
    @Override
    public boolean isSatisfiedBy(String password) {
//        return password.length() >= 8 && password.length() <= 16 && Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$",password);
        return password.length() >= 4;
    }

    @Override
    public void check(String password) throws ApiException {
        if (!isSatisfiedBy(password)) {
            throw ApiException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .errorCode(AuthErrorCode.PASSWORD_NOT_ENOUGH_CONDITION.getCode())
                    .errorMessage(AuthErrorCode.PASSWORD_NOT_ENOUGH_CONDITION.getMessage())
                    .build();
        }
    }
}
