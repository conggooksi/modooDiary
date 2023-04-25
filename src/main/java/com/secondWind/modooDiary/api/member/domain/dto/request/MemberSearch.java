package com.secondWind.modooDiary.api.member.domain.dto.request;

import com.secondWind.modooDiary.common.dto.PaginationDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class MemberSearch extends PaginationDto {

    private String nickName;

    public MemberSearch(Integer limit, Integer offset, String orderBy, Sort.Direction direction, String nickName) {
        super(limit, offset, orderBy, direction);
        this.nickName = nickName;
    }
}
