package com.modoodiary.modooDiary.api.diary.repository;

import com.modoodiary.modooDiary.api.diary.domain.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryCustomRepository{
    List<Diary> findAll();
}
