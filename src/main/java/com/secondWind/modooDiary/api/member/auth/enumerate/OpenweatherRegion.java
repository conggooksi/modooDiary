package com.secondWind.modooDiary.api.member.auth.enumerate;

import lombok.Getter;

@Getter
public enum OpenweatherRegion {
    SEOUL("서울", "37.49265", "126.8895972"),
    BUSAN("부산", "35.1798", "129.075"),
    INCHEON("인천", "37.45617301", "126.7059186"),
    GYEONGGI("경기", "37.567167", "127.190292"),
    DAEGU("대구", "35.798838", "128.583052"),
    DAEJEON("대전", "36.321655", "127.378953"),
    SEAJONG("세종", "36.48", "127.29"),
    CHOONGBUK("충복", "36.628503", "127.929344"),
    CHOONGNAM("충남", "36.557229", "126.779757"),
    JEONBUK("전북", "35.716705", "127.144185"),
    JEONNAM("전남", "34.819400", "126.893113"),
    GYEONGBUK("경북", "36.248647", "128.664734"),
    GYEONGNAM("경남", "35.259787", "128.664734"),
    JEJU("제주", "33.364805", "126.542671"),
    ULSAN("울산", "35.519301", "129.239078"),
    GWANGJU("광주", "35.126033", "126.831302");

    private final String region;
    private final String nx;
    private final String ny;

    public static OpenweatherRegion getDefault() {
        return OpenweatherRegion.SEOUL;
    }

    public static OpenweatherRegion fromString(String region) {
        try {
            return OpenweatherRegion.valueOf(region);
        } catch (IllegalArgumentException e) {
            return getDefault();
        }
    }

    OpenweatherRegion(String region, String nx, String ny) {
        this.region = region;
        this.nx = nx;
        this.ny = ny;
    }

//    @Override
//    public String toString() {
//        return region;
//    }
}
