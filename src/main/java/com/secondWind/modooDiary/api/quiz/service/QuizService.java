package com.secondWind.modooDiary.api.quiz.service;

import com.secondWind.modooDiary.api.quiz.domain.request.QuizResultRequest;
import com.secondWind.modooDiary.api.quiz.domain.response.DrawingQuizResponse;

import java.util.List;

public interface QuizService {
    List<DrawingQuizResponse> solvingDrawingQuiz();

    void updateQuizResult(QuizResultRequest quizResultRequest);
}
