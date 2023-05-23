package com.secondWind.modooDiary.api.diary.repository;

import com.secondWind.modooDiary.api.diary.domain.entity.link.StickerCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerCountRepository extends JpaRepository<StickerCount, Long> {

    StickerCount findByDiaryId(Long diaryId);
}
