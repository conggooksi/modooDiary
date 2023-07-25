package com.secondWind.modooDiary.common.component;

import com.secondWind.modooDiary.api.member.auth.domain.dto.GoogleInfoResponse;
import com.secondWind.modooDiary.api.member.auth.domain.dto.GoogleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GoogleLogin {

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.client.pw}")
    private String googleClientPw;

    public GoogleResponse getTokenByGoogleLogin(String authCode) {
        String url = "https://oauth2.googleapis.com/token";

        return WebClient.create(url)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("client_id", googleClientId)
                        .queryParam("client_secret", googleClientPw)
                        .queryParam("code", authCode)
                        .queryParam("redirect_uri", "http://localhost:8080/api/auth/oauth2/google")
                        .queryParam("grant_type", "authorization_code")
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(GoogleResponse.class)
                .block();
    }

    public GoogleInfoResponse getGoogleInfo(String authCode) {
        String url = "https://oauth2.googleapis.com/tokeninfo";

        return WebClient.create(url)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("id_token", this.getTokenByGoogleLogin(authCode).getId_token())
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(GoogleInfoResponse.class)
                .block();
    }
}
