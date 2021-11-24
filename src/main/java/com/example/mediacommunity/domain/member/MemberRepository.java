package com.example.mediacommunity.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByNickName(String nickName);
    List<Member> findAll();
    void clear();
}
