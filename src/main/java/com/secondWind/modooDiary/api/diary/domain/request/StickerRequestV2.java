package com.secondWind.modooDiary.api.diary.domain.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StickerRequestV2 {
    private Long diaryId;
    private Integer recommend;
    private Integer unlike;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public StickerRequestV2(Long diaryId, Integer recommend, Integer unlike) {
        this.diaryId = diaryId;
        this.recommend = recommend;
        this.unlike = unlike;
    }
}
