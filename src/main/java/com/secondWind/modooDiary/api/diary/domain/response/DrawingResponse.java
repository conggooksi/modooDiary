package com.secondWind.modooDiary.api.diary.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
public class DrawingResponse {
    private String displayUrl;
    private String deleteUrl;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public DrawingResponse(Long drawingId, String displayUrl, String deleteUrl) {
        this.displayUrl = displayUrl;
        this.deleteUrl = deleteUrl;
    }
}