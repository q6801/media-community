package com.example.mediacommunity.domain.member;

import java.util.List;

public interface MemberRepository {
    Member save(Member member);
    Member findById(Long id);
    Member findByNickName(String nickName);
    List<Member> findAll();
    void clear();
}
