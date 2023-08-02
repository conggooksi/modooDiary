package com.secondWind.modooDiary.api.member.auth.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoInfoResponse {
    private KakaoAccount kakao_account;

    @Data
    @NoArgsConstructor
    public class KakaoAccount {
        private String email;
    }
}
