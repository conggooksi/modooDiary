package com.secondWind.modooDiary.api.member.auth.enumerate;

import lombok.Getter;

@Getter
public enum OpenweatherRegion {
    SEOUL("서울", 37.49265F, 126.8895972F),
    BUSAN("부산", 35.1798F, 129.075F),
    INCHEON("인천", 37.45617301F, 126.7059186F),
    GYEONGGI("경기", 37.567167F, 127.190292F),
    DAEGU("대구", 35.798838F, 128.583052F),
    DAEJEON("대전", 36.321655F, 127.378953F),
    SEAJONG("세종", 36.48F, 127.29F),
    CHOONGBUK("충복", 36.628503F, 127.929344F),
    CHOONGNAM("충남", 36.557229F, 126.779757F),
    JEONBUK("전북", 35.716705F, 127.144185F),
    JEONNAM("전남", 34.819400F, 126.893113F),
    GYEONGBUK("경북", 36.248647F, 128.664734F),
    GYEONGNAM("경남", 35.259787F, 128.664734F),
    JEJU("제주", 33.364805F, 126.542671F),
    ULSAN("울산", 35.519301F, 129.239078F),
    GWANGJU("광주", 35.126033F, 126.831302F);

    private final String region;
    private final float nx;
    private final float ny;

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

    OpenweatherRegion(String region, Float nx, Float ny) {
        this.region = region;
        this.nx = nx;
        this.ny = ny;
    }

//    @Override
//    public String toString() {
//        return region;
//    }
}
