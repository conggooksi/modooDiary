package com.secondWind.modooDiary.common.component;

import com.secondWind.modooDiary.api.member.auth.enumerate.PublicRegion;
import com.secondWind.modooDiary.common.result.PublicWeatherResultResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class PublicWeatherSubscriber {

    private static final String serviceKey = "spDslsvoqEoOF8bCExVvcPRCqc0fqL9//Jt4q87Klb9GSzw6R9waE2uI2o7YHgZIEDXiAhNycfczlR9DIzKlYg==";

    public String getWeatherStatus(PublicRegion userRegion) {
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
                switch (rainyStatus) {
                    case "1" -> rainyStatus = "비";
                    case "2" -> rainyStatus = "비/눈";
                    case "3" -> rainyStatus = "눈";
                    case "5" -> rainyStatus = "빗방울";
                    case "6" -> rainyStatus = "빗방울눈날림";
                    case "7" -> rainyStatus = "눈날림";
                }

                return rainyStatus;
            } else if (sky.isPresent()) {
                String weatherStatus = sky.get().getFcstValue();
                switch (weatherStatus) {
                    case "1" -> weatherStatus = "맑음";
                    case "3" -> weatherStatus = "구름많음";
                    case "4" -> weatherStatus = "흐림";
                }
                return weatherStatus;
            }
        }

        return "모름";
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
