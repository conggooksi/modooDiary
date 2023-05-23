package com.secondWind.modooDiary.api.diary.domain.entity.link;

import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import com.secondWind.modooDiary.api.diary.domain.request.StickerRequest;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.common.entity.BaseEntity;
import com.secondWind.modooDiary.common.enumerate.Yn;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "sticker_count")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StickerCount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sticker_count_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private Long recommendCount;

    private Long unlikeCount;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public StickerCount(Long id, Diary diary, Long recommendCount, Long unlikeCount) {
        this.id = id;
        this.diary = diary;
        this.recommendCount = recommendCount;
        this.unlikeCount = unlikeCount;
    }

    @Builder(builderClassName = "createStickerCountBuilder", builderMethodName = "createStickerCountBuilder")
    public static StickerCount createStickerCount(Diary diary) {
        return of()
                .diary(diary)
                .recommendCount(0L)
                .unlikeCount(0L)
                .build();
    }

    public void plusSticker(StickerRequest stickerRequest) {
        if (Yn.Y.equals(stickerRequest.getRecommendYn())) {
            recommendCount++;
        }
        if (Yn.Y.equals(stickerRequest.getUnlikeYn())) {
            unlikeCount++;
        }
    }
}
