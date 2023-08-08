package com.secondWind.modooDiary.api.quiz.service;


import com.secondWind.modooDiary.api.diary.repository.DiaryRepository;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.api.member.repository.MemberRepository;
import com.secondWind.modooDiary.api.quiz.domain.entity.Quiz;
import com.secondWind.modooDiary.api.quiz.domain.request.QuizResultRequest;
import com.secondWind.modooDiary.api.quiz.domain.response.DrawingQuizResponse;
import com.secondWind.modooDiary.api.quiz.repository.QuizRepository;
import com.secondWind.modooDiary.common.enumerate.Yn;
import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.exception.code.MemberErrorCode;
import com.secondWind.modooDiary.common.exception.code.QuizErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizServiceImpl implements QuizService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final QuizRepository quizRepository;

    @Override
    public List<DrawingQuizResponse> solvingDrawingQuiz() {
        Long memberId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        Quiz quiz = quizRepository.findByMemberIdAndIsDeletedFalse(memberId);
        if (quiz != null && quiz.getIsEnable() == Yn.N) {
            throw ApiException.builder()
                    .errorMessage(QuizErrorCode.CANNOT_SOLVING_QUIZ.getMessage())
                    .errorCode(QuizErrorCode.CANNOT_SOLVING_QUIZ.getCode())
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        int quizCount = 4;
        List<DrawingQuizResponse> drawingQuizzes = diaryRepository.getDrawingQuiz();

        Random random = new Random();
        List<DrawingQuizResponse> randomQuizzes = new ArrayList<>();
        while (randomQuizzes.size() < quizCount) {
            int randomIndex = random.nextInt(drawingQuizzes.size());
            DrawingQuizResponse drawingQuizResponse = drawingQuizzes.get(randomIndex);
            if (!randomQuizzes.contains(drawingQuizResponse)) {
                randomQuizzes.add(drawingQuizResponse);
            }
        }

        return randomQuizzes;
    }

    @Override
    @Transactional
    public void updateQuizResult(QuizResultRequest quizResultRequest) {
        Long memberId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        Member member = memberRepository.findByIdAndIsDeletedFalse(memberId).orElseThrow(
                () -> ApiException.builder()
                        .errorCode(MemberErrorCode.NOT_FOUND_MEMBER.getCode())
                        .errorMessage(MemberErrorCode.NOT_FOUND_MEMBER.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());

        Quiz quiz = quizRepository.findByMemberIdAndIsDeletedFalse(memberId);
        if (quiz == null) {
            Quiz newQuiz = Quiz.createQuizBuilder()
                    .member(member)
                    .build();

            quiz = quizRepository.save(newQuiz);
        }

        if (Yn.Y.equals(quizResultRequest.getResultYn())) {
            quiz.plucScore();
        } else {
            quiz.changeIsEnableFalse();
        }
    }
}
