package com.secondWind.modooDiary.api.member.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberJoinDTO;
import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.exception.code.AuthErrorCode;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final Long AUTHENTICATION_EMAIL_EXPIRE_TIME = 1000 * 60 * 30L;
    private final JavaMailSender javaMailSender;
    private final StringRedisTemplate redisTemplate;
    private final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

    @Override
    public String sendEmailConfirm(String email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        String confirmKey = RandomStringUtils.randomNumeric(6);

        StringBuffer msg = new StringBuffer();
        msg.append("모두의 일기 이메일 인증입니다.");
        msg.append("\n");
        msg.append("아래의 코드를 입력해 주세요.");
        msg.append("\n");
        msg.append(confirmKey);

        try {
            message.addRecipients(Message.RecipientType.TO, email);
            message.setSubject("모두의 일기 이메일 인증입니다.");
            message.setText(msg.toString(), "utf-8");
            message.setFrom(new InternetAddress("${AdminMail.id}", "모두의 일기"));
            javaMailSender.send(message);
        } catch (Exception e) {
            throw ApiException.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .errorCode(AuthErrorCode.FAIL_SEND_CONFIRM_EMAIL.getCode())
                    .errorMessage(AuthErrorCode.FAIL_SEND_CONFIRM_EMAIL.getMessage())
                    .build();
        }

        return confirmKey;
    }

    @Override
    @Async
    public void sendAuthenticationEmail(MemberJoinDTO memberJoinDTO) {
        MimeMessage message = javaMailSender.createMimeMessage();
        String code = RandomStringUtils.randomNumeric(6);

        String content = "";

        Resource resource = pathMatchingResourcePatternResolver.getResource("mail-template/content.html");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

        } catch (Exception e) {

        }

        String content = outputStream.toString();
        content = content.replace("{code}", code);

        try {
            message.addRecipients(Message.RecipientType.TO, memberJoinDTO.getEmail());
            message.setSubject("모두의 일기 이메일 인증입니다.");
            message.setContent(content, "text/html; charset=utf-8");
            javaMailSender.send(message);
            log.info(memberJoinDTO.getEmail() + "로 이메일 전송에 성공하였습니다. " + "code: " + code);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            throw ApiException.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .errorCode(AuthErrorCode.FAIL_SEND_CONFIRM_EMAIL.getCode())
                    .errorMessage(AuthErrorCode.FAIL_SEND_CONFIRM_EMAIL.getMessage())
                    .build();
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String signUpRequestToRedis = objectMapper.writeValueAsString(memberJoinDTO);
            redisTemplate.opsForValue()
                    .set(code, signUpRequestToRedis, AUTHENTICATION_EMAIL_EXPIRE_TIME, TimeUnit.SECONDS);
            log.info("Redis에 회원가입 정보 저장 완료. Email : {}, code : {}", memberJoinDTO.getEmail(), code);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.info("Redis에 회원가입 정보 저장 실패. Fail User : {}", memberJoinDTO);

            throw ApiException.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .errorCode(AuthErrorCode.FAIL_TO_SIGN_UP.getCode())
                    .errorMessage(AuthErrorCode.FAIL_TO_SIGN_UP.getMessage())
                    .build();

        }
    }
}
