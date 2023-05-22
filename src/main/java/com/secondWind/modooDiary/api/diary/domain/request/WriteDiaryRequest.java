package com.secondWind.modooDiary.api.diary.domain.request;

import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import com.secondWind.modooDiary.api.diary.domain.entity.Weather;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class WriteDiaryRequest {
    @Min(value = 1, message = "회원 아이디가 필요합니다.")
    private Long memberId;

    @Length(min = 1, message = "제목이 필요합니다.")
    private String title;


    private String content;
    private String weather;
    private Float nx;
    private Float ny;

    public static Diary createDiary(WriteDiaryRequest writeDiaryRequest, Member member) {
        return Diary.createDiaryBuilder()
                .title(writeDiaryRequest.title)
                .weather(Weather.of().statusId(Long.parseLong(writeDiaryRequest.getWeather())).build())
                .content((writeDiaryRequest.content != null && !writeDiaryRequest.content.isBlank()) ? writeDiaryRequest.content : "제곧내")
                .member(member)
                .build();
    }
}
