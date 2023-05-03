package com.secondWind.modooDiary.common.component;

import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.result.WeatherResultResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WeatherSubscriber {

    private final String serviceKey = "spDslsvoqEoOF8bCExVvcPRCqc0fqL9//Jt4q87Klb9GSzw6R9waE2uI2o7YHgZIEDXiAhNycfczlR9DIzKlYg==";

    public WeatherResultResponse weatherSubscriber(String date, String time) {
        // 초 단기 일기 예보 정보 조회
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";
        return WebClient.create(url)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("pageNo", "1")
                        .queryParam("numOfRows", "60")
                        .queryParam("dataType", "JSON")
                        .queryParam("base_date", date)
                        .queryParam("base_time", time)
                        .queryParam("nx", "60")
                        .queryParam("ny", "127")
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(WeatherResultResponse.class)
                .block();
    }
}
