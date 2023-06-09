package com.secondWind.modooDiary.common.exception.code;

import lombok.Getter;

@Getter
public enum AuthErrorCode {
    FAILED_CONFIRM_EMAIL("FAILED_CONFIRM_EMAIL", "이메일 인증에 실패했습니다."),
    FAIL_SEND_CONFIMEAIL("FAIL_SEND_CONFIMEAIL", "인증 이메일을 발송하는데 실패했습니다."),

    EMAIL_NOT_ENOUGH_CONDITION("EMAIL_NOT_ENOUGH_CONDITION", "이메일 조건을 만족하지 못했습니다."),
    PASSWORD_NOT_ENOUGH_CONDITION("PASSWORD_NOT_ENOUGH_CONDITION", "패스워드 조건을 만족하지 못했습니다."),

    ALREADY_JOIN_USER("ALREADY_JOIN_USER", "이미 존재하는 아이디입니다."),
    DUPLICATE_NICKNAME("DUPLICATE_NICKNAME", "이미 존재하는 닉네임입니다."),

    NOT_AUTH_TOKEN("NOT_AUTH_TOKEN", "권한 정보가 없는 토큰입니다."),
    WRONG_TOKEN("WRONG_TOKEN", "잘못된 토큰입니다."),
    EXPIRED_TOKEN("EXPIRED_TOKEN", "기간이 만료된 토큰입니다."),
    UNSUPPORTED_TOKEN("UNSUPPORTED_TOKEN", "지원하지 않는 방식의 토큰입니다."),
    WRONG_TYPE_TOKEN("WRONG_TYPE_TOKEN", "잘못된 타입의 토큰입니다."),
    NOT_VALID_TOKEN("NOT_VALID_TOKEN", "Token이 유효하지 않습니다."),

    LOG_OUT_USER("LOG_OUT_USER", "로그아웃한 유저입니다."),

    ACCESS_DENIED("ACCESSDENIED", "접근 거부되었습니다."),

    NOT_MATCH_TOKEN_INFO("NOT_MATCH_TOKEN_INFO", "토큰의 유저 정보가 일치하지 않습니다."),
    BAD_REQUEST_BODY("BAD_REQUEST_BODY", ""),


    SECURITY_CONTEXT_NOT_FOUND("SECURITY_CONTEXT_NOT_FOUND", "Security Context에 인증 정보가 없습니다."),

    PERMISSION_DENIED("PERMISSION_DENIED", "접근 권한이 없습니다."),

    NOT_MATCH_PASSWORD("NOT_MATCH_PASSWORD", "패스워드가 일치하지 않습니다."),


    UNKNOWN_ERROR("UNKNOWN_ERROR", "알 수 없는 에러 발생");

    private final String code;
    private final String message;

    AuthErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
