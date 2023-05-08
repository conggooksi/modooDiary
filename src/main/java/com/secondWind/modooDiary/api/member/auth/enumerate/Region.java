package com.secondWind.modooDiary.api.member.auth.enumerate;

import lombok.Getter;

@Getter
public enum Region {
    SEOUL("서울", "60", "127"),
    BUSAN("부산", "98", "76"),
    INCHEON("인천", "55", "124"),
    ULSAN("울산", "102", "84"),
    GWANGJU("광주", "58", "74");

    private final String region;
    private final String nx;
    private final String ny;

    Region(String region, String nx, String ny) {
        this.region = region;
        this.nx = nx;
        this.ny = ny;
    }

    @Override
    public String toString() {
        return region;
    }
}
