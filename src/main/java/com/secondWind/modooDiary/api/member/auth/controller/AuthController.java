package com.secondWind.modooDiary.api.member.auth.controller;

import com.secondWind.modooDiary.api.diary.domain.request.MemberLoginDTO;
import com.secondWind.modooDiary.api.diary.domain.request.TokenDTO;
import com.secondWind.modooDiary.api.member.auth.domain.dto.*;
import com.secondWind.modooDiary.api.member.auth.service.AuthService;
import com.secondWind.modooDiary.api.member.auth.service.EmailService;
import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.exception.code.MemberErrorCode;
import com.secondWind.modooDiary.common.result.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthController {
    private final String BASIC_PREFIX = "Basic ";
    private final AuthService authService;


    @Operation(summary = "회원가입 API")
    @PostMapping("/signup")
    public void signup(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @Valid @RequestBody MemberJoinDTO memberJoinDTO) {

        if (authorization != null) {
            String authBasic = authorization.substring((BASIC_PREFIX.length()));

            String decodedAuthBasic = new String(Base64.getDecoder().decode(authBasic), StandardCharsets.UTF_8);

            String[] authUserInfo = decodedAuthBasic.split(":");

            String email = authUserInfo[0];
            String password = authUserInfo[1];

            memberJoinDTO.setEmail(email);
            memberJoinDTO.setPassword(password);

            authService.signup(memberJoinDTO);
        }
    }

    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestHeader(value = "isAdmin", required = false) Boolean isAdmin,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
            ) {

        if (authorization != null && !authorization.isBlank()) {
            String authBasic = authorization.substring(BASIC_PREFIX.length());

            String decodeAuthBasic = new String(Base64.getDecoder().decode(authBasic), StandardCharsets.UTF_8);
            String[] authUserInfo = decodeAuthBasic.split(":");

            String email = authUserInfo[0];
            String password = authUserInfo[1];

            if (isAdmin == null) {
                isAdmin = false;
            }

            MemberLoginDTO memberLoginDTO = MemberLoginDTO.of()
                    .email(email)
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

    @Operation(summary = "토큰 재발급 API")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Valid @RequestBody TokenRequestDTO tokenRequestDTO) {
        TokenDTO tokenDTO = authService.reissue(tokenRequestDTO);

        return ResponseHandler.generate()
                .data(tokenDTO)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "로그아웃 API")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody TokenRequestDTO tokenRequestDTO) {
        authService.logout(tokenRequestDTO);

        return ResponseHandler.generate()
                .data(null)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "비밀번호 변경 API")
    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(
            @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        authService.updatePassword(passwordUpdateRequest);

        return ResponseHandler.generate()
                .data(null)
                .status(HttpStatus.OK)
                .build();
    }

//    @Operation(summary = "이메일발송 API")
//    @PostMapping("/emailConfirm")
//    public ResponseEntity<?> emailConfirm(@RequestBody AuthenticationEmailRequest authenticationEmailRequest) {
//        String confirmKey = emailService.sendEmailConfirm(authenticationEmailRequest.getAuthenticationEmail());
//        //
//        return ResponseHandler.generate()
//                .data(confirmKey)
//                .status(HttpStatus.OK)
//                .build();
//    }

    @Operation(summary = "이메일 인증 API")
    @PostMapping("/check-code")
    public ResponseEntity<?> checkAuthenticationEmailCode(@RequestBody AuthenticationEmailRequest authenticationEmailRequest) {
        Long memberId = authService.registerMember(authenticationEmailRequest.getCode());

        return ResponseHandler.generate()
                .data(memberId)
                .status(HttpStatus.CREATED)
                .build();
    }
}
