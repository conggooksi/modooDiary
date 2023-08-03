package com.secondWind.modooDiary.api.quiz.domain.response;

import com.secondWind.modooDiary.api.diary.domain.entity.Drawing;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DrawingQuizResponse {
    private Long id;
    private String nickName;
    private String title;
    private String weather;
    private String content;
    private LocalDateTime createdTime;
    private Drawing drawing;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public DrawingQuizResponse(Long id, String nickName, String title, String weather, String content, Drawing drawing, LocalDateTime createdTime) {
        this.id = id;
        this.nickName = nickName;
        this.title = title;
        this.weather = weather;
        this.content = content;
        this.drawing = drawing;
        this.createdTime = createdTime;
    }

    public static DrawingQuizResponse toResponse(DrawingQuizResponse drawingQuizResponse) {
        return of()
                .id(drawingQuizResponse.getId())
                .nickName(drawingQuizResponse.getNickName())
                .title(drawingQuizResponse.getTitle())
                .weather(drawingQuizResponse.getWeather())
                .content(drawingQuizResponse.getContent())
                .createdTime(drawingQuizResponse.getCreatedTime())
                .build();
    }
}
