package com.example.mediacommunity.community.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


    @Test
    public void saveAndFindById() {
        //given
        Member member = getMemberList().get(0);

        //when
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findByLoginId(savedMember.getLoginId()).get();

        //then
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void SaveAndfindByNickName() {
        //given
        Member member = getMemberList().get(0);

        //when
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findByNickName(savedMember.getNickname()).get();

        //then
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        //given
        Member member0 = getMemberList().get(0);
        Member member1 = getMemberList().get(1);

        memberRepository.save(member0);
        memberRepository.save(member1);

        //when
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result).contains(member0, member1);
    }

    private List<Member> getMemberList() {
        return Arrays.asList(
                new Member("test121", "test1!", "HelloWorld1", "local", ""),
                new Member("test1232", "test!", "HelloWorld", "local", "")
        );
    }

}