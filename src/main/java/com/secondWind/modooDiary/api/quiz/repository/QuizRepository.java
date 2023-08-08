package com.secondWind.modooDiary.api.quiz.repository;

import com.secondWind.modooDiary.api.quiz.domain.entity.Quiz;
import com.secondWind.modooDiary.common.enumerate.Yn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizCustomRepository {
    Quiz findByMemberIdAndIsDeletedFalse(Long memberId);

    List<Quiz> findByIsEnableAndIsDeletedFalse(Yn n);
}
