package com.modoodiary.modooDiary.api.diary.domain.request;

import com.modoodiary.modooDiary.api.member.domain.entity.Member;
import com.modoodiary.modooDiary.common.dto.PaginationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class SearchDiary extends PaginationDto {
    private Long id;
    private Member member;
    private String title;
    private String weather;
    private String content;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public SearchDiary(Long id, Member member, String title, String weather, String content, Integer limit, Integer offset, String orderBy, Sort.Direction direction) {
        super(limit, offset, orderBy, direction);
        this.id = id;
        this.member = member;
        this.title = title;
        this.weather = weather;
        this.content = content;
    }
}
