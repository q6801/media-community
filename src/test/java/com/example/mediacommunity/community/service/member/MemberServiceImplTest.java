package com.example.mediacommunity.community.service.member;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberServiceImpl memberService;

    @Test
    void findMemberById() {
        //given
        Member savedMember = getStubMemberList().get(0);
        System.out.println("memberRepository = " + memberRepository);
        given(memberRepository.findByLoginId(savedMember.getLoginId())).willReturn(Optional.of(savedMember));

        //when
        Member foundMember = memberService.findMemberById(savedMember.getLoginId());

        //then
        Assertions.assertThat(foundMember).isEqualTo(savedMember);
    }

    @Test
    void findMemberByName() {
        //given
        Member savedMember = getStubMemberList().get(0);
        given(memberRepository.findByNickname(savedMember.getNickname())).willReturn(Optional.of(savedMember));

        //when
        Member foundMember = memberService.findMemberByName(savedMember.getNickname());

        //then
        Assertions.assertThat(foundMember).isEqualTo(savedMember);
    }

    @Test
    void findAllMembers() {
        //given
        Member savedMember1 = getStubMemberList().get(0);
        Member savedMember2= getStubMemberList().get(1);
        given(memberRepository.findAll()).willReturn(getStubMemberList());

        //when
        List<Member> allMembers = memberService.findAllMembers();

        //then
        Assertions.assertThat(allMembers).contains(savedMember1, savedMember2);
    }



    private List<Member> getStubMemberList() {
        return Arrays.asList(
                new Member("test121", "test1!", "HelloWorld1", "local", ""),
                new Member("test1232", "test!", "HelloWorld", "local", "")
        );
    }

}