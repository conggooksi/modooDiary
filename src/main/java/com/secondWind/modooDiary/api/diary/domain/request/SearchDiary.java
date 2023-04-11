package com.secondWind.modooDiary.api.diary.domain.request;

import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.common.dto.PaginationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class SearchDiary extends PaginationDto {
    private Long diaryId;
    private Long memberId;
    private String title;
    private String weather;
    private String content;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public SearchDiary(Long diaryId, Long memberId, String title, String weather, String content, Integer limit, Integer offset, String orderBy, Sort.Direction direction) {
        super(limit, offset, orderBy, direction);
        this.diaryId = diaryId;
        this.memberId = memberId;
        this.title = title;
        this.weather = weather;
        this.content = content;
    }
}
