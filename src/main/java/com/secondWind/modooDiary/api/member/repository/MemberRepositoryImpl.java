package com.secondWind.modooDiary.api.member.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondWind.modooDiary.api.member.auth.domain.dto.MemberResponseDTO;
import com.secondWind.modooDiary.api.member.domain.dto.request.MemberSearch;
import com.secondWind.modooDiary.api.member.domain.dto.response.MemberDetail;
import com.secondWind.modooDiary.api.member.domain.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.secondWind.modooDiary.api.diary.domain.entity.QDiary.diary;
import static com.secondWind.modooDiary.api.member.domain.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MemberResponseDTO> searchMember(MemberSearch memberSearch, PageRequest pageRequest) {
        List<MemberResponseDTO> content = queryFactory.select(Projections.constructor(MemberResponseDTO.class,
                        member.nickName))
                .from(member)
                .where(member.isDeleted.eq(0),
                        nickNameContains(memberSearch.getNickName()))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(memberSort(pageRequest))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(member.count())
                .from(member)
                .where(member.isDeleted.eq(0));

        return PageableExecutionUtils.getPage(content, pageRequest, countQuery::fetchOne);
    }

    @Override
    public Optional<MemberDetail> getMember(Long memberId) {
        return Optional.ofNullable(queryFactory.select(Projections.constructor(MemberDetail.class,
                        member.id,
                        member.email,
                        member.nickName))
                .from(member)
                .where(memberIdEq(memberId))
                .fetchOne());
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? member.id.eq(memberId) : null;
    }

    private OrderSpecifier<?> memberSort(PageRequest pageRequest) {
        if (!pageRequest.getSort().isEmpty()) {
            for (Sort.Order order : pageRequest.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "nickName":
                        return new OrderSpecifier<>(direction, member.nickName);
                }
            }
        }
        return new OrderSpecifier<>(Order.ASC, member.id);
    }

    private BooleanExpression nickNameContains(String nickName) {
        return nickName != null && nickName.isBlank() ? member.nickName.contains(nickName) : null;
    }
}
