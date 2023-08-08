package com.secondWind.modooDiary.api.quiz.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizCustomRepository {

    private final JPAQueryFactory queryFactory;
}
