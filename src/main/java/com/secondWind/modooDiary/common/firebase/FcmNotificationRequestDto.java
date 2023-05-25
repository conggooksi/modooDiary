package com.secondWind.modooDiary.common.firebase;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class FcmNotificationRequestDto {
    private Long targetUserId;
    private String title;
    private String body;
//    private String image;
//    private Map<String, String> data;

    public FcmNotificationRequestDto(Long targetUserId, String title, String body) {
        this.targetUserId = targetUserId;
        this.title = title;
        this.body = body;
    }
}
