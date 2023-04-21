package com.secondWind.modooDiary.api.member.auth.service;

import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberJoinDTO;
import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;
import com.secondWind.modooDiary.api.member.auth.domain.spec.PasswordSpecification;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.api.member.repository.MemberRepository;
import com.secondWind.modooDiary.common.exception.CustomAuthException;
import com.secondWind.modooDiary.common.exception.code.AuthErrorCode;
import com.secondWind.modooDiary.common.result.JsonResultData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final PasswordSpecification passwordSpecification;

    @Override
    @Transactional
    public MemberResponseDTO signup(MemberJoinDTO memberJoinDTO) {

        passwordSpecification.check(memberJoinDTO.getPassword());

        if (memberRepository.existsByLoginId(memberJoinDTO.getLoginId())) {
            throw new CustomAuthException(JsonResultData
                    .failResultBuilder()
                    .errorCode(AuthErrorCode.ALREADY_EXISTS_ID.getCode())
                    .errorMessage(AuthErrorCode.ALREADY_EXISTS_ID.getMessage())
                    .build());
        }


        Member member = memberJoinDTO.toMember(memberJoinDTO, passwordEncoder);

        memberRepository.save(member);


        return MemberResponseDTO.toResponse(member);
    }
}
