package com.secondWind.modooDiary.api.diary.domain.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@Schema(name = "일기 수정 요청", description = "일기 수정 요청 DTO")
public class UpdateDiaryRequest {

    @Min(value = 1, message = "회원 아이디가 필요합니다.")
    private Long memberId;

    @Length(min = 1, message = "제목이 필요합니다.")
    @Schema(description = "제목", nullable = true)
    private String title;

    @Schema(nullable = true)
    private String weather;

    @Schema(nullable = true)
    private String content;

    private boolean isAdmin;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public UpdateDiaryRequest(Long memberId, String title, String weather, String content) {
        this.memberId = memberId;
        this.title = title;
        this.weather = weather;
        this.content = content;
    }
}
