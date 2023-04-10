package com.modoodiary.modooDiary.api.diary.service;

import com.modoodiary.modooDiary.api.diary.domain.request.SearchDiary;
import com.modoodiary.modooDiary.api.diary.domain.response.DiaryResponse;

import java.util.List;

public interface DiaryService {
    List<DiaryResponse> getDiaries(SearchDiary searchDiary);

    Long writeDiary();
}
