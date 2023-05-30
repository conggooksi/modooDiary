package com.secondWind.modooDiary.api.diary.domain.response;

import com.secondWind.modooDiary.api.diary.domain.entity.Drawing;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DiaryResponse {
    private Long id;
    private String nickName;
    private String title;
    private String weather;
    private String content;
    private int recommendCount;
    private int unlikeCount;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    private List<Long> recommendedMemberIds;
    private Drawing drawing;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public DiaryResponse(Long id, String nickName, String title, String weather, String content, Drawing drawing, int recommendCount, int unlikeCount, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.nickName = nickName;
        this.title = title;
        this.weather = weather;
        this.content = content;
        this.drawing = drawing;
        this.recommendCount = recommendCount;
        this.unlikeCount = unlikeCount;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public static DiaryResponse toResponse(DiaryResponse diaryResponse) {
        return of()
                .id(diaryResponse.getId())
                .nickName(diaryResponse.getNickName())
                .title(diaryResponse.getTitle())
                .weather(diaryResponse.getWeather())
                .content(diaryResponse.getContent())
                .recommendCount(diaryResponse.getRecommendCount())
                .unlikeCount(diaryResponse.getUnlikeCount())
                .createdTime(diaryResponse.getCreatedTime())
                .updatedTime(diaryResponse.getUpdatedTime())
                .build();
    }
}
