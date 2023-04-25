package com.secondWind.modooDiary.api.member.service;

import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;
import com.secondWind.modooDiary.api.member.domain.dto.request.MemberSearch;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Page<MemberResponseDTO> getMembers(MemberSearch memberSearch) {
        PageRequest pageRequest = PageRequest.of(memberSearch.getOffset(), memberSearch.getLimit(), memberSearch.getDirection(), memberSearch.getOrderBy());
        return memberRepository.searchMember(memberSearch, pageRequest);
    }
}
