package com.secondWind.modooDiary.api.diary.domain.response;

import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DiaryDetail {
    private Long diaryId;
    private String nickName;
    private String title;
    private String weather;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public DiaryDetail(Long diaryId, String nickName, String title, String weather, String content, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.diaryId = diaryId;
        this.nickName = nickName;
        this.title = title;
        this.weather = weather;
        this.content = content;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public static DiaryDetail toDto(Diary diary) {
        return DiaryDetail.of()
                .diaryId(diary.getId())
                .nickName(diary.getMember().getNickName())
                .title(diary.getTitle())
                .weather(diary.getWeather())
                .content(diary.getContent())
                .createdTime(diary.getCreatedDate())
                .updatedTime(diary.getLastModifiedDate())
                .build();
    }
}
