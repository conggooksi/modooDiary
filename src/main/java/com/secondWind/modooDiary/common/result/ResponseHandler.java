package com.secondWind.modooDiary.common.result;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    @Builder(builderMethodName = "generate", builderClassName = "generate")
    public static ResponseEntity<JsonResultData<?>> generateResponse(Object data, HttpStatus status) {
        JsonResultData<Object> result = JsonResultData.successResultBuilder()
                .data(data)
                .build();

        return new ResponseEntity<>(result, status);
    }

    @Builder(builderMethodName = "failResultGenerate", builderClassName = "failResultGenerate")
    public static ResponseEntity<JsonResultData<?>> generateResponse(String errorMessage, String errorCode, HttpStatus status) {
        JsonResultData<Object> result = JsonResultData.failResultBuilder()
                .errorMessage(errorMessage)
                .errorCode(errorCode)
                .build();

        return new ResponseEntity<>(result, status);
    }


}
