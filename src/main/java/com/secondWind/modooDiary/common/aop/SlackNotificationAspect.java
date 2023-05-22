package com.secondWind.modooDiary.common.aop;

import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponseToSlack;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SlackNotificationAspect {
    private final SlackApi slackApi;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public SlackNotificationAspect(@Value("${slack.webhook}") String webhook,
            ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.slackApi = new SlackApi(webhook);
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

//    @Around("execution(* com.secondWind.modooDiary.api.diary.service.DiaryServiceImpl.writeDiary(..))")
//    public void slackNotificate(ProceedingJoinPoint pjp) throws Throwable {
//        Object returnResult = pjp.proceed();
//
//        threadPoolTaskExecutor.execute(() -> sendSlackMessage(returnResult));
//    }
//
//    private void sendSlackMessage(Object returnResult) {
//        SlackMessage slackMessage = new SlackMessage();
//        slackMessage.setText(returnResult.toString());
//
//        slackApi.call(slackMessage);
//    }

    @AfterReturning(pointcut = "execution(* com.secondWind.modooDiary.api.diary.service.DiaryServiceImpl.writeDiary(..))", returning = "result")
    public void afterWriteDiary(JoinPoint joinPoint, Object result) {

        threadPoolTaskExecutor.execute(() -> sendSlackMessage(result));
    }

    private void sendSlackMessage(Object result) {
        StringBuffer text = new StringBuffer();
        text.append("작성자 : ");
        text.append(((DiaryResponseToSlack) result).getNickName());
        text.append("\n");
        text.append("제 목 : ");
        text.append(((DiaryResponseToSlack) result).getTitle());
        text.append("\n");
        text.append("일 기 : ");
        text.append(((DiaryResponseToSlack) result).getContent());
        text.append("\n");
        text.append("https://modoo-diary.vercel.app/");
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.setText(text.toString());

        slackApi.call(slackMessage);
    }
}
