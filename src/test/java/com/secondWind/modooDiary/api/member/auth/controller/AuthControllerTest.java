package com.secondWind.modooDiary.api.member.auth.controller;

import com.secondWind.modooDiary.api.diary.domain.spec.AdminSpecification;
import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberJoinDTO;
import com.secondWind.modooDiary.api.member.auth.domain.spec.PasswordSpecification;
import com.secondWind.modooDiary.api.member.auth.service.AuthService;
import com.secondWind.modooDiary.api.member.auth.service.AuthServiceImpl;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.api.member.repository.MemberRepository;
import com.secondWind.modooDiary.common.provider.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthControllerTest {
    private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private  MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private  PasswordSpecification passwordSpecification = Mockito.mock(PasswordSpecification.class);
    private  AuthenticationManagerBuilder authenticationManagerBuilder = Mockito.mock(AuthenticationManagerBuilder.class);
    private  AdminSpecification adminSpecification = Mockito.mock(AdminSpecification.class);
    private  JwtTokenProvider jwtTokenProvider = Mockito.mock(JwtTokenProvider.class);
    private  StringRedisTemplate redisTemplate = Mockito.mock(StringRedisTemplate.class);

    @Test
    void signUpTest() {
        AuthService authService = new AuthServiceImpl(passwordEncoder, memberRepository, passwordSpecification, authenticationManagerBuilder, adminSpecification, jwtTokenProvider, redisTemplate);
        MemberJoinDTO memberJoinDTO = new MemberJoinDTO("kuk@gmail.com", "1234567", "테스트코드", "SEOUL");

        Member member = memberJoinDTO.toMember(memberJoinDTO, passwordEncoder);

        when(memberRepository.save(member)).thenAnswer((Answer<Member>) invocation -> {
            Member savedMember = invocation.getArgument(0);
            return savedMember;
        });

        Member newMember = memberRepository.save(member);

        System.out.println("member = " + newMember.getNickName());
    }

}