package com.example.mediacommunity.community.service.member;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {
    @Autowired
    MemberService memberService;

    @Test
    void save() {
        //given
        Member member = new Member(null, "test12!", "HelloWorld12", "local", "");

        //when

        //then
        assertThrows(RuntimeException.class, () -> memberService.save(member));
    }

    @Test
    void findMemberById() {
        //given
        Member savedMember = memberService.save(
                new Member("test123sfd", "test12!", "helloWorld", "local", ""));

        //when
        Member foundMember = memberService.findMemberById("test123sfd");

        //then
        Assertions.assertThat(foundMember).isEqualTo(savedMember);
    }

    @Test
    void failToFindMemberById() {
//        //given
//        String notMemberId = "test12";
//        //when
//        Member foundMember = memberService.findMemberById(notMemberId);
//
//        //then
//        Assertions.assertThat(foundMember).isEqualTo(true);
    }

    @Test
    void findMemberByName() {
        //given
        Member savedMember = memberService.save(
                new Member("test123sfd", "test12!", "helloWorld", "local", ""));

        //when
        Member foundMember = memberService.findMemberByName("helloWorld");

        //then
        Assertions.assertThat(foundMember).isEqualTo(savedMember);
    }

    @Test
    void findAllMembers() {
        //given
        Member savedMember1 = memberService.save(
                new Member("test1231", "test12!", "helloWorld1", "local", ""));
        Member savedMember2 = memberService.save(
                new Member("test1232", "test12!", "helloWorld2", "local", ""));

        //when
        List<Member> allMembers = memberService.findAllMembers();

        //then
        Assertions.assertThat(allMembers).contains(savedMember1, savedMember2);
    }
}