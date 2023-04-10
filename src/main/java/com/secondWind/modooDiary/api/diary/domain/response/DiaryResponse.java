package com.secondWind.modooDiary.api.diary.domain.response;

import com.secondWind.modooDiary.api.diary.domain.request.SearchDiary;
import lombok.Builder;
import lombok.Data;

@Data
public class DiaryResponse {
    private Long id;
    private String title;
    private String weather;
    private String content;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public DiaryResponse(Long id, String title, String weather, String content) {
        this.id = id;
        this.title = title;
        this.weather = weather;
        this.content = content;
    }

    public static DiaryResponse toResponse(SearchDiary searchDiary) {
        return of()
                .id(searchDiary.getId())
                .title(searchDiary.getTitle())
                .weather(searchDiary.getWeather())
                .content(searchDiary.getContent())
                .build();
    }
}
