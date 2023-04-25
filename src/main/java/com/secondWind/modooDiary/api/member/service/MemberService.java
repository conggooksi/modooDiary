package com.secondWind.modooDiary.api.member.service;

import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;
import com.secondWind.modooDiary.api.member.domain.dto.request.MemberSearch;
import org.springframework.data.domain.Page;

public interface MemberService {
    Page<MemberResponseDTO> getMembers(MemberSearch memberSearch);
}
