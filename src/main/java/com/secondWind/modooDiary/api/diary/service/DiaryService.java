package com.secondWind.modooDiary.api.diary.service;

import com.secondWind.modooDiary.api.diary.domain.request.DiaryRecommendRequest;
import com.secondWind.modooDiary.api.diary.domain.request.SearchDiary;
import com.secondWind.modooDiary.api.diary.domain.request.UpdateDiaryRequest;
import com.secondWind.modooDiary.api.diary.domain.request.WriteDiaryRequest;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryDetail;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponse;
import org.springframework.data.domain.Page;

public interface DiaryService {
    Page<DiaryResponse> getDiaries(SearchDiary searchDiary);

    Long writeDiary(WriteDiaryRequest writeDiaryRequest);

    Long updateDiary(Long diaryId, UpdateDiaryRequest updateDiaryRequest);

    void deleteDiary(Long diaryId);

    DiaryDetail getDiary(Long id);

    void updateDiaryRecommend(DiaryRecommendRequest diaryRecommendRequest);
}
