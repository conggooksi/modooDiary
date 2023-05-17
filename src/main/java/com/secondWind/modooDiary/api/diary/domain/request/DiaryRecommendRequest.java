package com.secondWind.modooDiary.api.diary.domain.request;

import com.secondWind.modooDiary.common.enumerate.Yn;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiaryRecommendRequest {

    private Long memberId;
    private Long diaryId;
    private Yn recommendYn;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public DiaryRecommendRequest(Long memberId, Long diaryId, Yn recommendYn) {
        this.memberId = memberId;
        this.diaryId = diaryId;
        this.recommendYn = recommendYn;
    }
}
