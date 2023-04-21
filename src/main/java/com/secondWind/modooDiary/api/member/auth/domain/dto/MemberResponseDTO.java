package com.secondWind.modooDiary.api.member.auth.domain.dto;

import com.secondWind.modooDiary.api.member.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberResponseDTO {

    private String nickName;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public MemberResponseDTO(String nickName) {
        this.nickName = nickName;
    }

    public static MemberResponseDTO toResponse(Member member) {
        return MemberResponseDTO.of()
                .nickName(member.getNickName())
                .build();
    }
}
