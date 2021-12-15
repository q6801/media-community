package com.example.mediacommunity.community.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByNickname(String nickname);
    List<Member> findAll();
//    void updateImageURL(String loginId, String url);
//    void updateNickname(String loginId, String nickname);
    void deleteMember(Member member);
}
