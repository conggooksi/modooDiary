package com.secondWind.modooDiary.api.member.domain.dto.response;

import com.secondWind.modooDiary.api.member.auth.enumerate.OpenweatherRegion;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDetail {
    private Long memberId;
    private String email;
    private String nickName;
    private OpenweatherRegion region;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public MemberDetail(Long memberId, String email, String nickName, OpenweatherRegion region) {
        this.memberId = memberId;
        this.email = email;
        this.nickName = nickName;
        this.region = region;
    }
}
