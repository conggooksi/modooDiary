package com.secondWind.modooDiary.api.diary.domain.response;

import com.secondWind.modooDiary.api.diary.domain.request.SearchDiary;
import lombok.Builder;
import lombok.Data;

@Data
public class DiaryResponse {
    private Long id;
    private String nickName;
    private String title;
    private String weather;
    private String content;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public DiaryResponse(Long id, String nickName, String title, String weather, String content) {
        this.id = id;
        this.nickName = nickName;
        this.title = title;
        this.weather = weather;
        this.content = content;
    }

    public static DiaryResponse toResponse(DiaryResponse diaryResponse) {
        return of()
                .id(diaryResponse.getId())
                .nickName(diaryResponse.getNickName())
                .title(diaryResponse.getTitle())
                .weather(diaryResponse.getWeather())
                .content(diaryResponse.getContent())
                .build();
    }
}
