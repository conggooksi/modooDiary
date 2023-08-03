package com.secondWind.modooDiary.api.quiz.service;


import com.secondWind.modooDiary.api.diary.repository.DiaryRepository;
import com.secondWind.modooDiary.api.quiz.domain.response.DrawingQuizResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizServiceImpl implements QuizService {
    private final DiaryRepository diaryRepository;
    @Override
    public List<DrawingQuizResponse> solvingDrawingQuiz() {
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
}
