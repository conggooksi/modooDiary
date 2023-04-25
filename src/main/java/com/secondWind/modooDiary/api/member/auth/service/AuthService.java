package com.secondWind.modooDiary.api.member.auth.service;

import com.secondWind.modooDiary.api.diary.domain.request.MemberLoginDTO;
import com.secondWind.modooDiary.api.diary.domain.request.TokenDTO;
import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberJoinDTO;
import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;
import com.secondWind.modooDiary.api.member.auth.domain.dto.PasswordUpdateRequest;
import com.secondWind.modooDiary.api.member.auth.domain.dto.TokenRequestDTO;

public interface AuthService {
    MemberResponseDTO signup(MemberJoinDTO memberJoinDTO);

    TokenDTO login(MemberLoginDTO memberLoginDTO);

    TokenDTO reissue(TokenRequestDTO tokenRequestDTO);

    void logout(TokenRequestDTO tokenRequestDTO);

    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
