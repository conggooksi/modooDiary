package com.secondWind.modooDiary.api.member.service;

import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;
import com.secondWind.modooDiary.api.member.domain.dto.request.EmailUpdateRequest;
import com.secondWind.modooDiary.api.member.domain.dto.request.MemberSearch;
import com.secondWind.modooDiary.api.member.domain.dto.request.NickNameUpdateRequest;
import com.secondWind.modooDiary.api.member.domain.dto.request.RegionUpdateRequest;
import com.secondWind.modooDiary.api.member.domain.dto.response.MemberDetail;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.api.member.repository.MemberRepository;
import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.exception.code.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Page<MemberResponseDTO> getMembers(MemberSearch memberSearch) {
        PageRequest pageRequest = PageRequest.of(memberSearch.getOffset(), memberSearch.getLimit(), memberSearch.getDirection(), memberSearch.getOrderBy());
        return memberRepository.searchMember(memberSearch, pageRequest);
    }

    @Override
    public MemberDetail getMember(Long memberId) {
        return memberRepository.getMember(memberId).orElseThrow(
                () -> ApiException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errorMessage(MemberErrorCode.NOT_FOUND_MEMBER.getMessage())
                        .errorCode(MemberErrorCode.NOT_FOUND_MEMBER.getCode())
                        .build());
    }

    @Override
    @Transactional
    public Long updateNickName(NickNameUpdateRequest nickNameUpdateRequest) {

        Member member = findById(nickNameUpdateRequest.getMemberId());
        member.changeNickName(nickNameUpdateRequest.getNickName());

        return member.getId();
    }

    @Override
    @Transactional
    public Long updateRegion(RegionUpdateRequest regionUpdateRequest) {
        Member member = findById(regionUpdateRequest.getMemberId());
        member.changeRegion(regionUpdateRequest.getRegion());
        return member.getId();
    }

    @Override
    @Transactional
    public Long updateEmail(EmailUpdateRequest emailUpdateRequest) {
        Member member = findById(emailUpdateRequest.getMemberId());
        member.changeEmail(emailUpdateRequest.getEmail());
        return member.getId();
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> ApiException.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errorMessage(MemberErrorCode.NOT_FOUND_MEMBER.getMessage())
                        .errorCode(MemberErrorCode.NOT_FOUND_MEMBER.getCode())
                        .build());
    }
}
