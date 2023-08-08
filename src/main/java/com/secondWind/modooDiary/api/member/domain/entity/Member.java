package com.secondWind.modooDiary.api.member.domain.entity;

import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import com.secondWind.modooDiary.api.diary.domain.entity.link.DiaryRecommend;
import com.secondWind.modooDiary.api.member.auth.enumerate.OpenweatherRegion;
import com.secondWind.modooDiary.api.quiz.domain.entity.Quiz;
import com.secondWind.modooDiary.common.enumerate.Authority;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String nickName;

    private String picture;

    @Enumerated(EnumType.STRING)
    private OpenweatherRegion region;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    private int isDeleted;

    private String lastAccessToken;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Diary> diaryList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<DiaryRecommend> diaryRecommendList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Quiz> quizList = new ArrayList<>();

    @Builder(builderClassName = "of", builderMethodName = "of")
    public Member(Long id, String email, String password, String nickName, String picture, OpenweatherRegion region, Authority authority, int isDeleted, List<Diary> diaryList) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.picture = picture;
        this.region = region;
        this.authority = authority;
        this.isDeleted = isDeleted;
        this.diaryList = diaryList;
    }

    public void changeLastAccessToken(String accessToken) {
        this.lastAccessToken = accessToken;
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public Member update(String name, String picture) {
        return Member.of()
                .nickName(name)
                .picture(picture)
                .build();
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeRegion(String region) {
        this.region = OpenweatherRegion.valueOf(region);
    }

    public void changeEmail(String email) {
        this.email = email;
    }
}
