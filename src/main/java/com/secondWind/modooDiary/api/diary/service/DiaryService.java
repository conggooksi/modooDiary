package com.secondWind.modooDiary.api.diary.service;

import com.secondWind.modooDiary.api.diary.domain.request.*;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponse;
import com.secondWind.modooDiary.api.diary.domain.response.DiaryResponseToSlack;
import org.springframework.data.domain.Page;

public interface DiaryService {
    Page<DiaryResponse> getDiaries(SearchDiary searchDiary);

    DiaryResponseToSlack writeDiary(WriteDiaryRequest writeDiaryRequest);

    Long updateDiary(Long diaryId, UpdateDiaryRequest updateDiaryRequest);

    void deleteDiary(Long diaryId);

    DiaryResponse getDiary(Long id);

    void updateDiaryRecommend(DiaryRecommendRequest diaryRecommendRequest);

    void updateSticker(StickerRequest stickerRequest);

    void updateStickerV2(StickerRequestV2 stickerRequest);
}
