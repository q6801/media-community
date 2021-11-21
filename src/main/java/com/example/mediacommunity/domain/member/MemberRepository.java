package com.example.mediacommunity.domain.member;

import java.util.List;

public interface MemberRepository {
    Member save(Member member);
    Member findByLoginId(String loginId);
    Member findByNickName(String nickName);
    List<Member> findAll();
    void deleteMember(Member member);
    void clear();
}
