package com.secondWind.modooDiary.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiaryErrorCode {
    APPIUM_COMMAND_SEND_FAIL("APPIUM_COMMAND_SEND_FAIL", "Failed to send appium command"),

    NOT_FOUND_DIARY("NOT_FOUND_DIARY", "일기를 찾을 수 없습니다.");

    private final String code;
    private final String message;
}
