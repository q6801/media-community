package com.example.mediacommunity.community.service.member;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberEditDto;
import com.example.mediacommunity.community.domain.member.SignUpDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    Boolean encodeAndSave(SignUpDto signUpDto);
    Member findMemberById(String loginId) ;
    Member findMemberByName(String nickName);
    List<Member> findAllMembers();
    Optional<String> updateProfile(String loginId, MemberEditDto member) throws IOException;
    void signOut(String memberId);
    void updateMemberRoleToUser(String memberId);
}
