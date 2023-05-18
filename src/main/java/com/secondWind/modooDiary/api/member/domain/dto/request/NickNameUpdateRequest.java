package com.secondWind.modooDiary.api.member.domain.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NickNameUpdateRequest {

    private Long memberId;
    private String nickName;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public NickNameUpdateRequest(Long memberId, String nickName) {
        this.memberId = memberId;
        this.nickName = nickName;
    }
}
