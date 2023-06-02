package com.secondWind.modooDiary.api.member.service;

import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;
import com.secondWind.modooDiary.api.member.domain.dto.request.EmailUpdateRequest;
import com.secondWind.modooDiary.api.member.domain.dto.request.MemberSearch;
import com.secondWind.modooDiary.api.member.domain.dto.request.NickNameUpdateRequest;
import com.secondWind.modooDiary.api.member.domain.dto.request.RegionUpdateRequest;
import com.secondWind.modooDiary.api.member.domain.dto.response.MemberDetail;
import org.springframework.data.domain.Page;

public interface MemberService {
    Page<MemberResponseDTO> getMembers(MemberSearch memberSearch);

    MemberDetail getMember(Long memberId);

    Long updateNickName(NickNameUpdateRequest nickNameUpdateRequest);

    Long updateRegion(RegionUpdateRequest regionUpdateRequest);

    Long updateEmail(EmailUpdateRequest idUpdateRequest);
}
