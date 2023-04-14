package com.secondWind.modooDiary.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiaryErrorCode {
    APPIUM_COMMAND_SEND_FAIL("APPIUM_COMMAND_SEND_FAIL", "Failed to send appium command");

    private final String code;
    private final String message;
}
