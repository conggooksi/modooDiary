package com.secondWind.modooDiary.common.component;

import com.secondWind.modooDiary.api.diary.domain.entity.Weather;
import com.secondWind.modooDiary.api.member.auth.enumerate.OpenweatherRegion;
import com.secondWind.modooDiary.common.result.OpenWeatherMapResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class OpenWeatherMapSubscriber {

    @Value("${weather.openweather}")
    private String serviceKey;

    public Weather getWeatherStatus(Float nx, Float ny) {
        OpenWeatherMapResultResponse weather = this.weatherSubscriber(nx, ny);
        OpenWeatherMapResultResponse.Weather resultWeather = weather.getCurrent().getWeather().get(0);

        return Weather.of()
                .statusId(resultWeather.getId().longValue())
                .main(resultWeather.getMain())
                .description(resultWeather.getDescription())
                .build();
    }

    public OpenWeatherMapResultResponse weatherSubscriber(Float nx, Float ny) {
        // 초 단기 일기 예보 정보 조회
        String url = "https://api.openweathermap.org/data/3.0/onecall";
        return WebClient.create(url)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", nx)
                        .queryParam("lon", ny)
                        .queryParam("exclude", "daily")
                        .queryParam("appid", serviceKey)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(OpenWeatherMapResultResponse.class)
                .block();
    }
}
