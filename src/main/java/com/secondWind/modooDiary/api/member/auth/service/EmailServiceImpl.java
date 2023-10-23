package com.secondWind.modooDiary.api.member.auth.service;

import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.exception.code.AuthErrorCode;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

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
}
