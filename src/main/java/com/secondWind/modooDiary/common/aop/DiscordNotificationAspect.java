package com.secondWind.modooDiary.common.aop;

import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponseToSlack;
import com.secondWind.modooDiary.common.component.DiscordWebhook;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@Aspect
@Component
@Slf4j
public class DiscordNotificationAspect {
    private final String webhook;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private DiscordWebhook discordWebhook;

    public DiscordNotificationAspect(@Value("${discord.webhook}") String webhook,
                                     ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.webhook = webhook;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @AfterReturning(pointcut = "execution(* com.secondWind.modooDiary.api.diary.service.DiaryServiceImpl.writeDiary(..))", returning = "result")
    public void afterWriteDiary(JoinPoint joinPoint, Object result) throws IOException {
        DiscordWebhook discordWebhook = new DiscordWebhook(webhook);
        DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();

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
        String displayUrl = ((DiaryResponseToSlack) result).getDisplayUrl();
        if (displayUrl != null) {
            text.append("\n");
            text.append(((DiaryResponseToSlack) result).getDisplayUrl());
        }

        embedObject.setImage(((DiaryResponseToSlack) result).getDisplayUrl());
        discordWebhook.setContent(text.toString());
//        discordWebhook.addEmbed(embedObject);
        discordWebhook.execute();

//        threadPoolTaskExecutor.execute(() -> sendDiscordMessage(result));
    }

    private void sendDiscordMessage(Object result) {




        MultiValueMap<String, Object> messageParams = createMessageParams((DiaryResponseToSlack) result);
        MultiValueMap<String, List<Object>> embedsParams = createEmbedsParams((DiaryResponseToSlack) result);

        LinkedMultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();


        WebClient.create(webhook)
                .post()
                .body(BodyInserters.fromMultipartData("embeds", messageParams))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(String.class)
                .block();
    }

    private static MultiValueMap<String, Object> createMessageParams(DiaryResponseToSlack result) {
        LinkedMultiValueMap<String, Object> messageParams = new LinkedMultiValueMap<>();

//        messageParams.add("content", text.toString());

        StringBuffer embeds = new StringBuffer();
        embeds.append("[{\"image\":{\"url\":\"");
        embeds.append(result.getDisplayUrl());
        embeds.append("\"}}]");
        messageParams.add("embeds", embeds);

        log.info(messageParams.toString());

        return messageParams;
    }

    private static MultiValueMap<String, List<Object>> createEmbedsParams(DiaryResponseToSlack result) {

        LinkedMultiValueMap<String, List<Object>> embedsParams = new LinkedMultiValueMap<>();
        LinkedMultiValueMap<String, String> imageUrl = new LinkedMultiValueMap<>();
        imageUrl.add("url", result.getDisplayUrl());

        Embed embed = new Embed();
        List<Object> embeds = new ArrayList<>();
        embed.setImage(imageUrl);
        embeds.add(embed);

        embedsParams.add("embeds", embeds);

        return embedsParams;
    }

    @Data
    private static class Embed {
        private LinkedMultiValueMap<String, String> Image;
    }
}
