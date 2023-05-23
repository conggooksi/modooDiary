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
@Table(name = "sticker")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sticker extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sticker_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private Yn recommendYn;

    @Enumerated(value = EnumType.STRING)
    private Yn unlikeYn;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public Sticker(Long id, Member member, Diary diary, Yn recommendYn, Yn unlikeYn) {
        this.id = id;
        this.member = member;
        this.diary = diary;
        this.recommendYn = recommendYn;
        this.unlikeYn = unlikeYn;
    }

    @Builder(builderClassName = "createStickerBuilder", builderMethodName = "createStickerBuilder")
    public static Sticker createDiaryRecommend(Member member, Diary diary, Yn recommendYn, Yn unlikeYn) {
        return of()
                .member(member)
                .diary(diary)
                .recommendYn(recommendYn)
                .unlikeYn(unlikeYn)
                .build();
    }

    public void changeSticker(StickerRequest stickerRequest) {
        this.recommendYn = stickerRequest.getRecommendYn();
        this.unlikeYn = stickerRequest.getUnlikeYn();
    }
}
