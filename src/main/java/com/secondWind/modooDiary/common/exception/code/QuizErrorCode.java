package com.secondWind.modooDiary.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizErrorCode {
    CANNOT_SOLVING_QUIZ("CANNOT_SOLVING_QUIZ", "퀴즈를 풀 수 없습니다.");

    private final String code;
    private final String message;
}
