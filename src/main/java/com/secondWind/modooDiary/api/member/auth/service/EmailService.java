package com.secondWind.modooDiary.api.member.auth.service;

import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberJoinDTO;

public interface EmailService {
    String sendEmailConfirm(String email);

    void sendAuthenticationEmail(MemberJoinDTO memberJoinDTO);
}
