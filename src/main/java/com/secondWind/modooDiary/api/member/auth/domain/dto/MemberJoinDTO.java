package com.secondWind.modooDiary.api.member.auth.domain.dto;

import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.api.member.domain.enumerate.Authority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class MemberJoinDTO {

    @Setter
    private String loginId;

    @Setter
    private String password;

    private String nickName;

    public Member toMember(MemberJoinDTO memberJoinDTO, PasswordEncoder passwordEncoder) {
        return Member.of()
                .loginId(memberJoinDTO.getLoginId())
                .password(passwordEncoder.encode(memberJoinDTO.getPassword()))
                .nickName(memberJoinDTO.getNickName())
                .authority(Authority.ROLE_USER)
                .build();
    }
}
