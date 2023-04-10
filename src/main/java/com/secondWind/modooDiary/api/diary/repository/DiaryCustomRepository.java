package com.secondWind.modooDiary.api.diary.repository;

import com.secondWind.modooDiary.api.diary.domain.request.SearchDiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DiaryCustomRepository {
    Page<SearchDiary> searchDiary(SearchDiary searchDiary, PageRequest pageRequest);
}
