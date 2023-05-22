package com.secondWind.modooDiary.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WeatherErrorCode {
    NOT_FOUND_WEATHER("NOT_FOUND_WEATHER", "날씨를 찾을 수 없습니다.");

    private final String code;
    private final String message;
}
