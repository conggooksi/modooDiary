package com.secondWind.modooDiary.api.diary.domain.entity.link;

import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
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
@Table(name = "diary_recommend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryRecommend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Enumerated(value = EnumType.STRING)
    private Yn recommendYn;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public DiaryRecommend(Long id, Member member, Diary diary, Yn recommendYn) {
        this.id = id;
        this.member = member;
        this.diary = diary;
        this.recommendYn = recommendYn;
    }

    @Builder(builderClassName = "createDiaryRecommendBuilder", builderMethodName = "createDiaryRecommendBuilder")
    public static DiaryRecommend createDiaryRecommend(Member member, Diary diary) {
        return of()
                .member(member)
                .diary(diary)
                .recommendYn(Yn.Y)
                .build();
    }

    public void changeRecommendYn(Yn recommendYn) {
        this.recommendYn = recommendYn;
    }
}
