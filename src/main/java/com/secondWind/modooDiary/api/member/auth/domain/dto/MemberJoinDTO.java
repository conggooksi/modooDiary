package com.secondWind.modooDiary.api.member.auth.domain.dto;

import com.secondWind.modooDiary.api.member.auth.enumerate.Region;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.common.enumerate.Authority;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class MemberJoinDTO {

    @Setter
    private String email;

    @Setter
    private String password;

    private String nickName;

    private String region;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public MemberJoinDTO(String email, String password, String nickName, String region) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.region = region;
    }

    public Member toMember(MemberJoinDTO memberJoinDTO, PasswordEncoder passwordEncoder) {
        return Member.of()
                .email(memberJoinDTO.getEmail())
                .password(passwordEncoder.encode(memberJoinDTO.getPassword()))
                .nickName(memberJoinDTO.getNickName())
                .region(((memberJoinDTO.getRegion() != null) && (!memberJoinDTO.getRegion().isBlank())) ? Region.valueOf(memberJoinDTO.getRegion()) : Region.SEOUL)
                .authority(Authority.ROLE_USER)
                .build();
    }
}
