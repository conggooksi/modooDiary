package com.secondWind.modooDiary.api.member.auth.service;

import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberJoinDTO;
import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;

public interface AuthService {
    MemberResponseDTO signup(MemberJoinDTO memberJoinDTO);
}
