package com.secondWind.modooDiary.api.member.auth.controller;

import com.secondWind.modooDiary.api.diary.domain.request.MemberLoginDTO;
import com.secondWind.modooDiary.api.diary.domain.request.TokenDTO;
import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberJoinDTO;
import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;
import com.secondWind.modooDiary.api.member.auth.service.AuthService;
import com.secondWind.modooDiary.common.exception.code.MemberErrorCode;
import com.secondWind.modooDiary.common.result.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Tag(name = "auth", description = "인증 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final String BASIC_PREFIX = "Basic ";
    private final AuthService authService;


    @Operation(summary = "회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @Valid @RequestBody MemberJoinDTO memberJoinDTO) {

        if (authorization != null) {
            String authBasic = authorization.substring((BASIC_PREFIX.length()));

            String decodedAuthBasic = new String(Base64.getDecoder().decode(authBasic), StandardCharsets.UTF_8);

            String[] authUserInfo = decodedAuthBasic.split(":");

            String loginId = authUserInfo[0];
            String password = authUserInfo[1];

            memberJoinDTO.setLoginId(loginId);
            memberJoinDTO.setPassword(password);

            MemberResponseDTO nickName = authService.signup(memberJoinDTO);

            return ResponseHandler.generate()
                    .data(nickName)
                    .status(HttpStatus.CREATED)
                    .build();
        } else {
            return ResponseHandler.failResultGenerate()
                    .errorMessage(MemberErrorCode.ENTERED_ID_AND_PASSWORD.getMessage())
                    .errorCode(MemberErrorCode.ENTERED_ID_AND_PASSWORD.getCode())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        }
    }

    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestHeader("isAdmin") Boolean isAdmin,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
            ) {

        if (authorization != null && !authorization.isBlank()) {
            String authBasic = authorization.substring(BASIC_PREFIX.length());

            String decodeAuthBasic = new String(Base64.getDecoder().decode(authBasic), StandardCharsets.UTF_8);
            String[] authUserInfo = decodeAuthBasic.split(":");

            String loginId = authUserInfo[0];
            String password = authUserInfo[1];

            MemberLoginDTO memberLoginDTO = MemberLoginDTO.of()
                    .loginId(loginId)
                    .password(password)
                    .isAdmin(isAdmin)
                    .build();

            TokenDTO tokenDTO = authService.login(memberLoginDTO);

            return ResponseHandler.generate()
                    .data(tokenDTO)
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return ResponseHandler.failResultGenerate()
                    .errorCode(MemberErrorCode.ENTERED_ID_AND_PASSWORD.getCode())
                    .errorMessage(MemberErrorCode.ENTERED_ID_AND_PASSWORD.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

}
