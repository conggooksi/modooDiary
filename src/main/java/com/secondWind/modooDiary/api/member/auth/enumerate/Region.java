package com.secondWind.modooDiary.api.member.auth.enumerate;

import lombok.Getter;

@Getter
public enum Region {
    SEOUL("서울", "60", "127");

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
