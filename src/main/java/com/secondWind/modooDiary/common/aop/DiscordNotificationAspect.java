package com.secondWind.modooDiary.common.aop;

import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponseToSlack;
import com.secondWind.modooDiary.common.component.DiscordWebhook;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
@Component
@Slf4j
public class DiscordNotificationAspect {
    private final String webhook;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public DiscordNotificationAspect(@Value("${discord.webhook}") String webhook,
                                     ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.webhook = webhook;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @AfterReturning(pointcut = "execution(* com.secondWind.modooDiary.api.diary.service.DiaryServiceImpl.writeDiary(..))", returning = "result")
    public void afterWriteDiary(JoinPoint joinPoint, Object result) throws IOException {
        threadPoolTaskExecutor.execute(() -> sendDiscordMessage((DiaryResponseToSlack) result));
    }

    private void sendDiscordMessage(DiaryResponseToSlack result){
        DiscordWebhook discordWebhook = new DiscordWebhook(webhook);
        DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();

        StringBuffer text = new StringBuffer();
        text.append("https://modoo-diary.vercel.app/");
        text.append("\\r\\n");
        text.append("작성자 : ");
        text.append(result.getNickName());
        text.append("\\r\\n");
        text.append("제    목 : ");
        text.append(result.getTitle());
        text.append("\\r\\n");
        text.append("일    기 : ");
        text.append(result.getContent().replaceAll("\n", "\\\\n" + String.format("%15s","")));
        discordWebhook.setContent(text.toString());

        if (result.getDisplayUrl() != null) {
            embedObject.setImage(result.getDisplayUrl());
            discordWebhook.addEmbed(embedObject);
        }
        try {
            discordWebhook.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
