package com.secondWind.modooDiary.api.member.repository;

import com.secondWind.modooDiary.api.member.domain.entity.Member;
import com.secondWind.modooDiary.common.exception.ApiException;
import com.secondWind.modooDiary.common.exception.CustomAuthException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    boolean existsByLoginId(String loginId);

    Optional<Member> findByLoginId(String username);
}
