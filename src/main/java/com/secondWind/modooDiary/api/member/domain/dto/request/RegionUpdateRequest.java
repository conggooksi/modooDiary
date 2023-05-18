package com.secondWind.modooDiary.api.member.domain.dto.request;

import com.secondWind.modooDiary.api.member.auth.enumerate.Region;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegionUpdateRequest {
    private Long memberId;
    private String region;
}
