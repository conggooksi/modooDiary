package com.secondWind.modooDiary.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiaryErrorCode {
    NOT_FOUND_DIARY("NOT_FOUND_DIARY", "일기를 찾을 수 없습니다."),
    NOT_AUTHORIZATION_DIARY("NOT_AUTHORIZATION_DIARY", "일기를 수정할 권한이 없습니다.");

    private final String code;
    private final String message;
}
