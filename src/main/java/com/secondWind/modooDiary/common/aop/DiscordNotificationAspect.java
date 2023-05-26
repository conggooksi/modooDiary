package com.secondWind.modooDiary.common.aop;

import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponseToSlack;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Aspect
@Component
public class DiscordNotificationAspect {
    private final String webhook;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public DiscordNotificationAspect(@Value("${discord.webhook}") String webhook,
                                     ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.webhook = webhook;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @AfterReturning(pointcut = "execution(* com.secondWind.modooDiary.api.diary.service.DiaryServiceImpl.writeDiary(..))", returning = "result")
    public void afterWriteDiary(JoinPoint joinPoint, Object result) {

        threadPoolTaskExecutor.execute(() -> sendDiscordMessage(result));
    }

    private void sendDiscordMessage(Object result) {
        StringBuffer text = new StringBuffer();
        text.append("https://modoo-diary.vercel.app/");
        text.append("\n");
        text.append("작성자 : ");
        text.append(((DiaryResponseToSlack) result).getNickName());
        text.append("\n");
        text.append("제  목 : ");
        text.append(((DiaryResponseToSlack) result).getTitle());
        text.append("\n");
        text.append("일  기 : ");
        text.append(((DiaryResponseToSlack) result).getContent());

        WebClient.create(webhook)
                .post()
                .body(BodyInserters.fromFormData("content", text.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(String.class)
                .block();
    }
}
