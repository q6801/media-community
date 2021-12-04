package com.example.mediacommunity.community.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        Member member = new Member("test12", "test12!", "HelloWorld12", "local");
        //when
        Member savedMember = memberRepository.save(member);
        //then
        Member findMember = memberRepository.findByLoginId(savedMember.getLoginId()).get();
        System.out.println("findMember = " + findMember);
        System.out.println("savedMember = " + savedMember);
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void SaveAndfindByNickName() {
        //given
        Member member = new Member("test1232", "test!", "HelloWorld", "local");
        //when
        Member savedMember = memberRepository.save(member);
        //then
        Member findMember = memberRepository.findByNickName(savedMember.getNickname()).get();
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        //given
        Member member1 = new Member("test121", "test1!", "HelloWorld1", "local");
        Member member2 = new Member("test212", "test2!", "HelloWorld2", "local");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result).contains(member1, member2);
    }
}