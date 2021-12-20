package com.example.mediacommunity.community.service.member;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberEditDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member save(Member member);
    Member findMemberById(String loginId) ;
    Member findMemberByName(String nickName);
    List<Member> findAllMembers();
    Boolean updateNickname(String loginId, String newNickname);
    Optional<String> updateProfile(String loginId, MemberEditDto member) throws IOException;
    void signOut(Member member);
}
