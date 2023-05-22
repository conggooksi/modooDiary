package com.secondWind.modooDiary.api.diary.repository;

import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import com.secondWind.modooDiary.api.diary.domain.entity.link.DiaryRecommend;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRecommendRepository extends JpaRepository<DiaryRecommend, Long> {
    DiaryRecommend findByMemberAndDiary(Member member, Diary diary);

    List<DiaryRecommend> findByDiaryId(Long id);
}
