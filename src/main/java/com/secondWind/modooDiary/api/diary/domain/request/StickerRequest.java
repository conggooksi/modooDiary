package com.secondWind.modooDiary.api.diary.domain.request;

import com.secondWind.modooDiary.common.enumerate.Yn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StickerRequest {

    private Long memberId;
    private Long diaryId;
    private Yn recommendYn;
    private Yn unlikeYn;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public StickerRequest(Long memberId, Long diaryId, Yn recommendYn, Yn unlikeYn) {
        this.memberId = memberId;
        this.diaryId = diaryId;
        this.recommendYn = recommendYn;
        this.unlikeYn = unlikeYn;
    }
}
