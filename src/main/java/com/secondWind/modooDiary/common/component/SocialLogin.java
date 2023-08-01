package com.secondWind.modooDiary.common.component;

import com.secondWind.modooDiary.api.member.auth.domain.dto.GoogleInfoResponse;
import com.secondWind.modooDiary.api.member.auth.domain.dto.GoogleResponse;
import com.secondWind.modooDiary.api.member.auth.domain.dto.NaverInfoResponse;
import com.secondWind.modooDiary.api.member.auth.domain.dto.NaverResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class SocialLogin {

    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.client.pw}")
    private String googleClientPw;
    @Value("${naver.client.id}")
    private String naverClientId;
    @Value("${naver.client.pw}")
    private String naverClientPw;

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

    public NaverResponse getTokenByNaverLogin(String authCode) {
        String url = "https://nid.naver.com";

        return WebClient.create(url)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2.0/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", naverClientId)
                        .queryParam("client_secret", naverClientPw)
                        .queryParam("code", authCode)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(NaverResponse.class)
                .block();
    }

    public NaverInfoResponse.Response getNaverInfo(String authCode) {
        String url = "https://openapi.naver.com";

        NaverInfoResponse naverInfoResponse = WebClient.create(url)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("v1/nid/me")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + this.getTokenByNaverLogin(authCode).getAccess_token())
                .retrieve()
                .bodyToMono(NaverInfoResponse.class)
                .block();

        return naverInfoResponse.getResponse();
    }
}
