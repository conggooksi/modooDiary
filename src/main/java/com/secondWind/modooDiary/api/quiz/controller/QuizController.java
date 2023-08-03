package com.secondWind.modooDiary.api.quiz.controller;

import com.secondWind.modooDiary.api.quiz.domain.response.DrawingQuizResponse;
import com.secondWind.modooDiary.api.quiz.service.QuizService;
import com.secondWind.modooDiary.common.result.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "quiz", description = "quiz API")
@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
@Slf4j
public class QuizController {

    private final QuizService quizService;

    @Operation(summary = "그림 quiz API")
    @GetMapping("")
    public ResponseEntity<?> solvingDrawingQuiz() {
        List<DrawingQuizResponse> drawingQuizResponse = quizService.solvingDrawingQuiz();

        return ResponseHandler.generate()
                .data(drawingQuizResponse)
                .status(HttpStatus.OK)
                .build();
    }
}
