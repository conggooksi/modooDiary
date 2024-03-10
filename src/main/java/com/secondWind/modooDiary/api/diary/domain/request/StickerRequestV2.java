package com.secondWind.modooDiary.api.diary.domain.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StickerRequestV2 {

    private Long memberId;
    private Long diaryId;
    private Integer recommend;
    private Integer unlike;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public StickerRequestV2(Long memberId, Long diaryId, Integer recommendYn, Integer unlikeYn) {
        this.memberId = memberId;
        this.diaryId = diaryId;
        this.recommend = recommendYn;
        this.unlike = unlikeYn;
    }
}
