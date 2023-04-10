package com.modoodiary.modooDiary.api.diary.repository;

import com.modoodiary.modooDiary.api.diary.domain.request.SearchDiary;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import static com.secondWind.modooDiary.api.diary.domain.entity.QDiary.diary;

@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchDiary> searchDiary(SearchDiary searchDiary, PageRequest pageRequest) {
        queryFactory.select()
                .from(diary);

        return null;
    }
}
