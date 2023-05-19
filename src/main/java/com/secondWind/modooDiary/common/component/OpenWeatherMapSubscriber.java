package com.secondWind.modooDiary.common.component;

import com.secondWind.modooDiary.api.diary.domain.entity.Weather;
import com.secondWind.modooDiary.api.diary.repository.WeatherRepository;
import com.secondWind.modooDiary.api.member.auth.enumerate.OpenweatherRegion;
import com.secondWind.modooDiary.api.member.auth.enumerate.PublicRegion;
import com.secondWind.modooDiary.common.result.OpenWeatherMapResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class OpenWeatherMapSubscriber {

    private static final String serviceKey = "13f95afecd7b5c2aff4b5104efd0e701";
    private final WeatherRepository weatherRepository;

    public Weather getWeatherStatus(OpenweatherRegion userRegion) {
        OpenWeatherMapResultResponse weather = this.weatherSubscriber(userRegion);
        OpenWeatherMapResultResponse.Weather resultWeather = weather.getCurrent().getWeather().get(0);

        return Weather.of()
                .statusId((long) Math.round(resultWeather.getId()))
                .main(resultWeather.getMain())
                .description(resultWeather.getDescription())
                .build();
    }

    public OpenWeatherMapResultResponse weatherSubscriber(OpenweatherRegion userRegion) {
        // 초 단기 일기 예보 정보 조회
        String url = "https://api.openweathermap.org/data/3.0/onecall";
        return WebClient.create(url)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", Float.parseFloat(userRegion.getNx()))
                        .queryParam("lon", Float.parseFloat(userRegion.getNy()))
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
