package com.secondWind.modooDiary.api.quiz.domain.entity;

import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.common.entity.BaseEntity;
import com.secondWind.modooDiary.common.enumerate.Yn;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Quiz extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="quiz_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private Integer score;
    @Enumerated(value = EnumType.STRING)
    private Yn isEnable;
    private int isDeleted;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public Quiz(Long id, Member member, Integer score, Yn isEnable, int isDeleted) {
        this.id = id;
        this.member = member;
        this.score = score;
        this.isEnable = isEnable;
        this.isDeleted = isDeleted;
    }

    @Builder(builderClassName = "createQuizBuilder", builderMethodName = "createQuizBuilder")
    public static Quiz createQuiz(Member member) {
        return of()
                .member(member)
                .score(0)
                .isEnable(Yn.Y)
                .build();
    }

    public void plucScore() {
        this.score++;
    }

    public void changeIsEnableFalse() {
        this.isEnable = Yn.N;
    }

    public void changeIsEnableTrue() {
        this.isEnable = Yn.Y;
    }
}
