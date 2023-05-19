package com.secondWind.modooDiary.api.diary.domain.response;

import com.secondWind.modooDiary.api.diary.domain.entity.Weather;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DiaryResponse {
    private Long id;
    private String nickName;
    private String title;
    private String weatherDescription;
    private String content;
    private int recommendCount;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public DiaryResponse(Long id, String nickName, String title, String weatherDescription, String content, int recommendCount,LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.nickName = nickName;
        this.title = title;
        this.weatherDescription = weatherDescription;
        this.content = content;
        this.recommendCount = recommendCount;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public static DiaryResponse toResponse(DiaryResponse diaryResponse) {
        return of()
                .id(diaryResponse.getId())
                .nickName(diaryResponse.getNickName())
                .title(diaryResponse.getTitle())
                .weatherDescription(diaryResponse.getWeatherDescription())
                .content(diaryResponse.getContent())
                .recommendCount(diaryResponse.getRecommendCount())
                .createdTime(diaryResponse.getCreatedTime())
                .updatedTime(diaryResponse.getUpdatedTime())
                .build();
    }
}
