package com.modoodiary.modooDiary.api.diary.service;

import com.modoodiary.modooDiary.api.diary.domain.entity.Diary;
import com.modoodiary.modooDiary.api.diary.domain.request.SearchDiary;
import com.modoodiary.modooDiary.api.diary.domain.response.DiaryResponse;
import com.modoodiary.modooDiary.api.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    @Override
    @Transactional
    public List<DiaryResponse> getDiaries(SearchDiary searchDiary) {
        PageRequest pageRequest = PageRequest.of(searchDiary.getOffset(), searchDiary.getLimit(), searchDiary.getDirection(), searchDiary.getOrderBy());

        Page<SearchDiary> diaries = diaryRepository.searchDiary(searchDiary, pageRequest);

        return diaries.stream().map(DiaryResponse::toResponse).collect(Collectors.toList());
    }

    @Override
    public Long writeDiary() {
        return null;
    }
}
