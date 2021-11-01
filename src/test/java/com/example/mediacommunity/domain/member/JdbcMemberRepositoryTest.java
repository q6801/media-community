package com.example.mediacommunity.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

//    @AfterEach
    public void afterEach() {
        memberRepository.clear();
    }

    @Test
    public void saveAndFindById() {
        //given
        Member member = new Member("test12", "test12!", "HelloWorld12");
        //when
        Member savedMember = memberRepository.save(member);
        //then
        Member findMember = memberRepository.findById(savedMember.getId());
        System.out.println("findMember = " + findMember);
        System.out.println("savedMember = " + savedMember);
        assertThat(findMember).isNotEqualTo(savedMember);
    }

    @Test
    void SaveAndfindByNickName() {
        //given
        Member member = new Member("test", "test!", "HelloWorld");
        //when
        Member savedMember = memberRepository.save(member);
        //then
        Member findMember = memberRepository.findByNickName(savedMember.getNickname());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member("test1", "test1!", "HelloWorld1");
        Member member2 = new Member("test2", "test2!", "HelloWorld2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1, member2);
    }
}