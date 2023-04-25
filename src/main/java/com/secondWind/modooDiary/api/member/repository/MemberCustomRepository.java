package com.secondWind.modooDiary.api.member.repository;

import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;
import com.secondWind.modooDiary.api.member.domain.dto.request.MemberSearch;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MemberCustomRepository {
    Page<MemberResponseDTO> searchMember(MemberSearch memberSearch, PageRequest pageRequest);
}
