package com.secondWind.modooDiary.api.diary.domain.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class UpdateDiaryRequest {

    @NotNull
    private Long diaryId;
    @Min(value = 1, message = "회원 아이디가 필요합니다.")
    private Long memberId;

    @Length(min = 1, message = "제목이 필요합니다.")
    private String title;

    private String weather;

    private String content;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public UpdateDiaryRequest(Long memberId, String title, String weather, String content) {
        this.memberId = memberId;
        this.title = title;
        this.weather = weather;
        this.content = content;
    }
}
