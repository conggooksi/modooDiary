package com.secondWind.modooDiary.api.diary.domain.entity;

import com.secondWind.modooDiary.api.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor
@Getter
@Where(clause = "is_deleted = false")
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String title;
    private String weather;
    private String content;
    private int isDeleted;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public Diary(Long diaryId, Member member, String title, String weather, String content, int isDeleted) {
        this.id = diaryId;
        this.member = member;
        this.title = title;
        this.weather = weather;
        this.content = content;
        this.isDeleted = isDeleted;
    }

    @Builder(builderMethodName = "updateDiaryBuilder", builderClassName = "updateDiaryBuilder")
    public void changeDiary(Member member, String title, String weather, String content) {
        this.member = member;
        this.title = title;
        this.weather = weather;
        this.content = content;
    }

//    @Builder(builderMethodName = "deleteDiaryBuilder", builderClassName = "deleteDiaryBuilder")
    public void deleteDiary() {
        this.isDeleted = 1;
    }
}
