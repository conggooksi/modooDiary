package com.secondWind.modooDiary.api.member.auth.domain.dto;

import lombok.Getter;

@Getter
public class TokenRequestDTO {
    private String accessToken;
    private String refreshToken;
}
