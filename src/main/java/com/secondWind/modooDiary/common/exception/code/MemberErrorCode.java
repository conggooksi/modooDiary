package com.secondWind.modooDiary.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {
    NOT_FOUND_MEMBER("NOT_FOUND_MEMBER", "회원을 찾을 수 없습니다."),

    ENTERED_ID_AND_PASSWORD("ENTERED_ID_AND_PASSWORD", "아이디와 비밀번호를 입력해주세요.");

    private final String code;
    private final String message;
}
