package com.modoodiary.modooDiary.api.diary.repository;

import com.modoodiary.modooDiary.api.diary.domain.request.SearchDiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DiaryCustomRepository {
    Page<SearchDiary> searchDiary(SearchDiary searchDiary, PageRequest pageRequest);
}
