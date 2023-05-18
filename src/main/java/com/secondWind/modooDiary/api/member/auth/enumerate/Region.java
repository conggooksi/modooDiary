package com.secondWind.modooDiary.api.member.auth.enumerate;

import lombok.Getter;

@Getter
public enum Region {
    SEOUL("서울", "60", "127"),
    BUSAN("부산", "98", "76"),
    INCHEON("인천", "55", "124"),
    GYEONGGI("경기", "60", "120"),
    DAEGU("대구", "89", "90"),
    DAEJEON("대전", "67", "100"),
    SEAJONG("세종", "66", "103"),
    CHOONGBUK("충복", "76", "114"),
    CHOONGNAM("충남", "68", "100"),
    JEONBUK("전북", "63", "89"),
    JEONNAM("전남", "51", "67"),
    GYEONGBUK("경북", "89", "91"),
    GYEONGNAM("경남", "91", "77"),
    JEJU("제주", "52", "38"),
    ULSAN("울산", "102", "84"),
    GWANGJU("광주", "58", "74");

    private final String region;
    private final String nx;
    private final String ny;

    public static Region getDefault() {
        return Region.SEOUL;
    }

    public static Region fromString(String region) {
        try {
            return Region.valueOf(region);
        } catch (IllegalArgumentException e) {
            return getDefault();
        }
    }

    Region(String region, String nx, String ny) {
        this.region = region;
        this.nx = nx;
        this.ny = ny;
    }

//    @Override
//    public String toString() {
//        return region;
//    }
}
