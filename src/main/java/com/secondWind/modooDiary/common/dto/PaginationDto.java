package com.secondWind.modooDiary.common.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PaginationDto {

    private Integer limit;
    private Integer offset;
    private String orderBy;
    private Sort.Direction direction;

    public PaginationDto(Integer limit, Integer offset, String orderBy, Sort.Direction direction) {
        this.limit = limit == null? 10 : limit;
        this.offset = offset == null? 0 : offset;
        this.orderBy = orderBy == null? "id" : orderBy;
        this.direction = direction == null? Sort.Direction.ASC : direction;
    }
}
