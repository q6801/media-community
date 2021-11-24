package com.example.mediacommunity.service.member;

import com.example.mediacommunity.domain.member.LoginDto;
import com.example.mediacommunity.domain.member.Member;
import com.example.mediacommunity.domain.member.SignUpDto;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member save(Member member);
    Member findMemberById(String loginId) ;
    Member findMemberByName(String nickName);
    List<Member> findAllMembers();
    Member login(LoginDto loginDto, BindingResult bindingResult);
    void signUp(SignUpDto signUpDto, BindingResult bindingResult);
    void clear();
}
