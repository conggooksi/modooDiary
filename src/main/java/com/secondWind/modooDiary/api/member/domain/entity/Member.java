package com.secondWind.modooDiary.api.member.domain.entity;

import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String nickName;
    private int isDeleted;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Diary> diaryList = new ArrayList<>();


    @Builder(builderClassName = "of", builderMethodName = "of")
    public Member(Long id, String loginId, String password, String nickName, int isDeleted, List<Diary> diaryList) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.nickName = nickName;
        this.isDeleted = isDeleted;
        this.diaryList = diaryList;
    }
}
