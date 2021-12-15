package com.example.mediacommunity.community.service.member;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberEditDto;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member save(Member member);
    Member findMemberById(String loginId) ;
    Member findMemberByName(String nickName);
    List<Member> findAllMembers();
    Optional<String> updateProfile(String loginId, MemberEditDto member, BindingResult bindingResult) throws IOException;
    void signOut(Member member);
}
