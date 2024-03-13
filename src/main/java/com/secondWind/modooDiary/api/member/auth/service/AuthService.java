package com.secondWind.modooDiary.api.member.auth.service;

import com.secondWind.modooDiary.api.diary.domain.request.MemberLoginDTO;
import com.secondWind.modooDiary.api.diary.domain.request.TokenDTO;
import com.secondWind.modooDiary.api.member.auth.domain.dto.*;

public interface AuthService {
    void signup(MemberJoinDTO memberJoinDTO);

    TokenDTO login(MemberLoginDTO memberLoginDTO);

    TokenDTO reissue(TokenRequestDTO tokenRequestDTO);

    void logout(TokenRequestDTO tokenRequestDTO);

    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);

    TokenDTO loginByGoogle(String authCode);

    TokenDTO loginByNaver(String authCode);

    TokenDTO loginByKakao(String authCode);

    Long registerMember(String code);
}
