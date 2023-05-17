package com.secondWind.modooDiary.api.diary.repository;

import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import com.secondWind.modooDiary.api.diary.domain.entity.link.DiaryRecommend;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRecommendRepository extends JpaRepository<DiaryRecommend, Long> {
    DiaryRecommend findByMemberAndDiary(Member member, Diary diary);
}
