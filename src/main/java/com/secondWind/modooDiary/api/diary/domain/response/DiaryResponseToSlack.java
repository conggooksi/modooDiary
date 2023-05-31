package com.secondWind.modooDiary.api.diary.domain.response;

import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import lombok.Builder;
import lombok.Data;

@Data
public class DiaryResponseToSlack {
    private Long diaryId;
    private String nickName;
    private String title;
    private String content;
    private String displayUrl;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public DiaryResponseToSlack(Diary diary) {
        this.diaryId = diary.getId();
        this.nickName = diary.getMember().getNickName();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        if (diary.getDrawing() != null) this.displayUrl = diary.getDrawing().getDisplayUrl();
    }
}
