package com.secondWind.modooDiary.api.member.auth.service;

import com.secondWind.modooDiary.api.diary.domain.request.MemberLoginDTO;
import com.secondWind.modooDiary.api.diary.domain.request.TokenDTO;
import com.secondWind.modooDiary.api.diary.domain.spec.AdminSpecification;
import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberJoinDTO;
import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;
import com.secondWind.modooDiary.api.member.auth.domain.spec.PasswordSpecification;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.api.member.repository.MemberRepository;
import com.secondWind.modooDiary.common.exception.CustomAuthException;
import com.secondWind.modooDiary.common.exception.code.AuthErrorCode;
import com.secondWind.modooDiary.common.provider.JwtTokenProvider;
import com.secondWind.modooDiary.common.result.JsonResultData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final PasswordSpecification passwordSpecification;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final AdminSpecification adminSpecification;

    private final JwtTokenProvider jwtTokenProvider;

    private final StringRedisTemplate redisTemplate;

    @Override
    @Transactional
    public MemberResponseDTO signup(MemberJoinDTO memberJoinDTO) {

        passwordSpecification.check(memberJoinDTO.getPassword());

        if (memberRepository.existsByLoginId(memberJoinDTO.getLoginId())) {
            throw new CustomAuthException(JsonResultData
                    .failResultBuilder()
                    .errorCode(AuthErrorCode.ALREADY_JOIN_USER.getCode())
                    .errorMessage(AuthErrorCode.ALREADY_JOIN_USER.getMessage())
                    .build());
        }


        Member member = memberJoinDTO.toMember(memberJoinDTO, passwordEncoder);

        memberRepository.save(member);


        return MemberResponseDTO.toResponse(member);
    }

    @Override
    @Transactional
    public TokenDTO login(MemberLoginDTO memberLoginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = memberLoginDTO.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        if (memberLoginDTO.getIsAdmin()) {
            adminSpecification.check(authentication);
        }

        return getTokenDTO(authentication);
    }

    private TokenDTO getTokenDTO(Authentication authentication) {
        TokenDTO tokenDTO = jwtTokenProvider.generateTokenDTO(authentication);

        redisTemplate.opsForValue()
                .set("RT: " + authentication.getName(),
                        tokenDTO.getRefreshToken(),
                        tokenDTO.getAccessTokenExpiresIn(),
                        TimeUnit.MILLISECONDS);

        Optional<Member> optionalMember = memberRepository.findById(Long.valueOf(authentication.getName()));
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.changeLastAccessToken(tokenDTO.getAccessToken());
        }

        return tokenDTO;
    }
}
