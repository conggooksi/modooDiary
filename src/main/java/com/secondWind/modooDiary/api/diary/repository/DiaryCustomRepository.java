package com.secondWind.modooDiary.api.diary.repository;

import com.secondWind.modooDiary.api.diary.domain.request.SearchDiary;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponse;
import com.secondWind.modooDiary.api.quiz.domain.response.DrawingQuizResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface DiaryCustomRepository {
    Page<DiaryResponse> searchDiary(SearchDiary searchDiary, PageRequest pageRequest);

    Optional<DiaryResponse> getDiary(Long diaryId);

    List<DrawingQuizResponse> getDrawingQuiz();
}
