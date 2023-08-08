package com.secondWind.modooDiary.api.member.repository;

import com.secondWind.modooDiary.api.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    boolean existsByEmailAndIsDeletedFalse(String email);

    Optional<Member> findByEmail(String email);

    boolean existsByNickNameAndIsDeletedFalse(String nickName);

    Optional<Member> findByEmailAndIsDeletedFalse(String email);

    Optional<Member> findByIdAndIsDeletedFalse(Long memberId);
}
