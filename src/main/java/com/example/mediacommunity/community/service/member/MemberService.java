package com.example.mediacommunity.community.service.member;

import com.example.mediacommunity.community.domain.member.Member;

import java.util.List;

public interface MemberService {
    Member save(Member member);
    Member findMemberById(String loginId) ;
    Member findMemberByName(String nickName);
    List<Member> findAllMembers();
//    Member login(LoginDto loginDto, BindingResult bindingResult);
//    void signUp(SignUpDto signUpDto, BindingResult bindingResult);
    void clear();
    void signOut(Member member);
}
