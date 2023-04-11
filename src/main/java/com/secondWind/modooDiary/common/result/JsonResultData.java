package com.secondWind.modooDiary.common.result;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JsonResultData<T> {

    private T data;
    private ErrorInfo error;

    @Getter
    public static class ErrorInfo {
        private String code;
        private String message;

        @Builder
        public ErrorInfo(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    @Builder(builderMethodName = "successResultBuilder", builderClassName = "successResultBuilder")
    public JsonResultData(T data, ErrorInfo error) {
        this.data = data;
        this.error = null;
    }

    @Builder(builderMethodName = "failResultBuilder", builderClassName = "failResultBuilder")
    public JsonResultData(String errorCode, String errorMessage) {
        this.data = null;
        this.error = ErrorInfo.builder()
                .code(errorCode)
                .message(errorMessage)
                .build();
    }
}
