package com.secondWind.modooDiary.common.exception;

import com.secondWind.modooDiary.common.result.JsonResultData;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SlackException extends RuntimeException{
    private JsonResultData errorEntity;
    private HttpStatus status;

    @Builder
    public SlackException(String errorMessage, String errorCode, HttpStatus status) {
        super(errorMessage);
        this.errorEntity = JsonResultData.failResultBuilder()
                .errorMessage(errorMessage)
                .errorCode(errorCode)
                .build();
        this.status = status;
    }
}
