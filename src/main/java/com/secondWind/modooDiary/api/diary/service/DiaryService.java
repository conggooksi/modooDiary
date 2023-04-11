package com.secondWind.modooDiary.api.diary.service;

import com.secondWind.modooDiary.api.diary.domain.request.SearchDiary;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiaryService {
    Page<DiaryResponse> getDiaries(SearchDiary searchDiary);

    Long writeDiary();
}
