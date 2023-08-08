package com.secondWind.modooDiary.common.scheduler;

import com.secondWind.modooDiary.api.quiz.domain.entity.Quiz;
import com.secondWind.modooDiary.api.quiz.repository.QuizRepository;
import com.secondWind.modooDiary.common.enumerate.Yn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuizScheduler {

    private final QuizRepository quizRepository;

    @Async
    @Scheduled(cron = "0 0 0/1 * * *")
    @Transactional
    public void resetQuizEnabled() {
        List<Quiz> quizzes = quizRepository.findByIsEnableAndIsDeletedFalse(Yn.N);
        quizzes.forEach(Quiz::changeIsEnableTrue);
    }

    // todo 일간 스케쥴러, 월간 스케쥴러 추가

}
