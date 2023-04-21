package com.secondWind.modooDiary.common.exception.code;

import lombok.Getter;

@Getter
public enum AuthErrorCode {

    PASSWORD_NOT_ENOUGH_CONDITION("PASSWORD_NOT_ENOUGH_CONDITIOIN", "패스워드 조건을 만족하지 못했습니다."),

    ALREADY_EXISTS_ID("ALREADY_EXISTS_ID", "이미 존재하는 아이디입니다.");

    private final String code;
    private final String message;

    AuthErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
