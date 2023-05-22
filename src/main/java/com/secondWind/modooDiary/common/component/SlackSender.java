package com.secondWind.modooDiary.common.component;

import com.secondWind.modooDiary.common.exception.SlackException;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SlackSender {

    @Value("${slack.token}")
    private String serviceKey;

    public void slackSender(String nickName, String title) {
        StringBuffer text = new StringBuffer();
        text.append("작성자 : ");
        text.append(nickName);
        text.append("\n");
        text.append(" 제 목 : ");
        text.append(title);

        String url = "https://hooks.slack.com/services/";
        WebClient.create(url)
                .post()
                .uri(serviceKey)
                .bodyValue(Text.of().text(text.toString()).build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(error ->
                                Mono.error(SlackException.builder()
                                        .errorMessage(error)
                                        .errorCode("SLACK_SENDER_ERROR")
                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .build())))
//                        .map(Exception::new))
                .bodyToMono(String.class)
                .block();
    }

        @Data
        @Builder(builderMethodName = "of", builderClassName = "of")
        private static class Text {
            private String text;
        }
}
