package com.secondWind.modooDiary.api.diary.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
public class DiaryResponseToSlack {
    private Long diaryId;
    private String nickName;
    private String title;
    private String content;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public DiaryResponseToSlack(Long diaryId, String nickName, String title, String content) {
        this.diaryId = diaryId;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
    }
}
