package com.secondWind.modooDiary.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OpenWeatherMapResultResponse {
    private Double lat;
    private Double lon;
    private String timezone;
    private Double timezone_offset;
    private Current current;
    private List<Hourly> hourly;

    @Data
    public static class Current {
        private Double dt;
        private Double sunrise;
        private Double sunset;
        private Double temp;
        private Double feels_like;
        private Double pressure;
        private Double humidity;
        private Double dew_point;
        private Double uvi;
        private Double clouds;
        private Double visibility;
        private Double wind_speed;
        private Double wind_deg;
        private Double wind_gust;
        private List<Weather> weather;
    }

    @Data
    public static class Weather {
        private Double id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class Minutely {
        private Double dt;
        private Double precipitation;
    }

    @Data
    public static class Hourly {
        private Double dt;
        private Double temp;
        private Double feels_like;
        private Double pressure;
        private Double humidity;
        private Double dew_point;
        private Double uvi;
        private Double clouds;
        private Double visibility;
        private Double wind_speed;
        private Double wind_deg;
        private Double wind_gust;
        private List<Weather> weather;
        private Double pop;
        private Snow snow;
    }

    @Data
    public static class Snow {
        private Long hour;
    }
}
