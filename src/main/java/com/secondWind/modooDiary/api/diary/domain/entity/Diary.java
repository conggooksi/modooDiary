package com.secondWind.modooDiary.api.diary.domain.entity;

import com.secondWind.modooDiary.api.diary.domain.entity.link.DiaryRecommend;
import com.secondWind.modooDiary.api.diary.domain.entity.link.Sticker;
import com.secondWind.modooDiary.api.diary.domain.entity.link.StickerCount;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Where(clause = "is_deleted = false")
public class Diary extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Weather weather;
    private String content;
    private int recommendCount;
    private int isDeleted;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryRecommend> diaryRecommendLIst = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "diary_id")
    private StickerCount stickerCount;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public Diary(Long diaryId, Member member, String title, Weather weather, String content, int recommendCount, int isDeleted, List<DiaryRecommend> diaryRecommendLIst) {
        this.id = diaryId;
        this.member = member;
        this.title = title;
        this.weather = weather;
        this.content = content;
        this.recommendCount = recommendCount;
        this.isDeleted = isDeleted;
        this.diaryRecommendLIst = diaryRecommendLIst;
    }

    @Builder(builderMethodName = "createDiaryBuilder", builderClassName = "createDiaryBuilder")
    public Diary(Member member, String title, Weather weather, String content) {
        this.member = member;
        this.title = title;
        this.weather = weather;
        this.content = content;
        this.recommendCount = 0;
    }

    @Builder(builderMethodName = "updateDiaryBuilder", builderClassName = "updateDiaryBuilder")
    public void changeDiary(String title, String content) {
        if (title != null) this.title = title;
        if (content != null) this.content = content;
    }

//    @Builder(builderMethodName = "deleteDiaryBuilder", builderClassName = "deleteDiaryBuilder")
    public void deleteDiary() {
        this.isDeleted = 1;
    }

    public void plusRecommendCount() {
        this.recommendCount++;
    }

    public void minusRecommendCount() {
        this.recommendCount--;
    }
}
