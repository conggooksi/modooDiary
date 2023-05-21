package com.secondWind.modooDiary.common.component;

import com.secondWind.modooDiary.api.diary.domain.entity.Weather;
import com.secondWind.modooDiary.api.member.auth.enumerate.PublicRegion;
import com.secondWind.modooDiary.common.result.PublicWeatherResultResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class PublicWeatherSubscriber {

    @Value("${weather.publicweather}")
    private String serviceKey;

    public Weather getWeatherStatus(PublicRegion userRegion) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        String date = currentDateTime.format(dateFormatter);
        String time = currentDateTime.format(timeFormatter);

        int checkTime = Integer.parseInt(time);

        if (checkTime % 100 < 30) {
            checkTime -= 100;
        }

        time = String.format("%04d", checkTime);

        PublicWeatherResultResponse weather = this.weatherSubscriber(userRegion, date, time);

        List<PublicWeatherResultResponse.Response.Item> items = weather.getResponse().getBody().getItems().getItem();
        Optional<PublicWeatherResultResponse.Response.Item> pty = items.stream().filter(item -> item.getCategory().equals("PTY")).findFirst();
        Optional<PublicWeatherResultResponse.Response.Item> sky = items.stream().filter(item -> item.getCategory().equals("SKY")).findFirst();

        if (pty.isPresent()) {
            String rainyStatus = pty.get().getFcstValue();
            if (!rainyStatus.equals("0")) {
                return Weather.of()
                        .statusId(switch (rainyStatus) {
                                    case "1" -> 501L;
                                    case "2" -> 616L;
                                    case "3" -> 601L;
                                    case "6" -> 300L;
                                    case "7" -> 600L;
                                    default -> 500L;})
                        .build();
            } else if (sky.isPresent()) {
                String weatherStatus = sky.get().getFcstValue();
                return Weather.of()
                        .statusId(switch (weatherStatus) {
                                    case "3" -> 802L;
                                    case "4" -> 804L;
                                    default -> 800L;})
                        .build();
            }
        }
        return Weather.of()
                .statusId(800L)
                .build();
    }

    public PublicWeatherResultResponse weatherSubscriber(PublicRegion userRegion, String date, String time) {
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
                        .queryParam("nx", userRegion.getNx())
                        .queryParam("ny", userRegion.getNy())
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(PublicWeatherResultResponse.class)
                .block();
    }
}
