package com.secondWind.modooDiary.api.member.domain.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailUpdateRequest {

    private Long memberId;
    private String email;

    @Builder(builderClassName = "of", builderMethodName = "of")
    public EmailUpdateRequest(Long memberId, String email) {
        this.memberId = memberId;
        this.email = email;
    }
}
