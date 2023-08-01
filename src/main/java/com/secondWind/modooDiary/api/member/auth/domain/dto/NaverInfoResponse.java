package com.secondWind.modooDiary.api.member.auth.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NaverInfoResponse {
    private String resultcode;
    private String message;
    private Response response;

    @Data
    @NoArgsConstructor
    public class Response {
        private String name;
        private String email;
        private String nickname;
        private String profile_image;

    }
}
