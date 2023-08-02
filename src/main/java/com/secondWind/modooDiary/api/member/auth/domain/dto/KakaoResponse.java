package com.secondWind.modooDiary.api.member.auth.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;
}
