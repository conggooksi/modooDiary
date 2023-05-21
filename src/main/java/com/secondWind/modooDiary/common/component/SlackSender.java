package com.secondWind.modooDiary.common.component;

import com.secondWind.modooDiary.api.diary.domain.entity.Weather;
import com.secondWind.modooDiary.api.member.auth.enumerate.OpenweatherRegion;
import com.secondWind.modooDiary.common.result.OpenWeatherMapResultResponse;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class SlackSender {

    @Value("${slack.token}")
    private String serviceKey;

    public void slackSender(String nickName, String title) {
        String url = "https://hooks.slack.com/services/";
        WebClient.create(url)
                .post()
                .uri(serviceKey)
                .bodyValue("'text'" + ":" + " '" + nickName + "의 일기" + " " + "제목:" + title + "'")
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(String.class)
                .block();
    }

        @Data
        @Builder(builderMethodName = "of", builderClassName = "of")
        private static class Text {
            private String writer;
            private String title;
        }
}
