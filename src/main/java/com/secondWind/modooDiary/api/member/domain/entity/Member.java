package com.secondWind.modooDiary.api.member.domain.entity;

import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import com.secondWind.modooDiary.api.member.auth.enumerate.Region;
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
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Region region;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    private int isDeleted;

    private String lastAccessToken;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Diary> diaryList = new ArrayList<>();


    @Builder(builderClassName = "of", builderMethodName = "of")
    public Member(Long id, String loginId, String password, String nickName, Region region, Authority authority, int isDeleted, List<Diary> diaryList) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.nickName = nickName;
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
}
