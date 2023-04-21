package com.secondWind.modooDiary.common.exception;

import com.secondWind.modooDiary.common.result.JsonResultData;
import lombok.Getter;

@Getter
public class CustomAuthException extends RuntimeException {

    private JsonResultData errorEntity;

    public CustomAuthException(JsonResultData errorEntity) {
        super(errorEntity.getError().getMessage());
        this.errorEntity = errorEntity;
    }
}
