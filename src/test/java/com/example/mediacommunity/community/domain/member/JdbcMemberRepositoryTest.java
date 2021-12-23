//package com.example.mediacommunity.community.domain.member;
//
//import com.example.mediacommunity.community.domain.board.Board;
//import com.example.mediacommunity.community.repository.member.MemberRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class JdbcMemberRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Test
//    public void saveAndPwCompare() {
//        //given
//        Member member = getMemberList().get(0);
//
//        //when
//        Member savedMember = memberRepository.save(member);
//        String encode = bCryptPasswordEncoder.encode(member.getPassword());
//
//        //then
//        assertThat(savedMember.getPassword()).isEqualTo(encode);
//    }
//
//    @Test
//    public void saveAndFindById() {
//        //given
//        Member member = getMemberList().get(0);
//        member.setBoards(getStubBoardList());
//        Optional<Member> test1 = memberRepository.findByLoginId("test1");
//
//        //when
//        Member savedMember = memberRepository.save(member);
//        Member findMember = memberRepository.findByLoginId(savedMember.getLoginId()).get();
//
//        //then
//        assertThat(findMember).isEqualTo(savedMember);
//    }
//
//    @Test
//    void SaveAndfindByNickName() {
//        //given
//        Member member = getMemberList().get(0);
//
//        //when
//        Member savedMember = memberRepository.save(member);
//        Member findMember = memberRepository.findByNickname(savedMember.getNickname()).get();
//
//        //then
//        assertThat(findMember).isEqualTo(savedMember);
//    }
//
//    @Test
//    void findAll() {
//        //given
//        Member member0 = getMemberList().get(0);
//        Member member1 = getMemberList().get(1);
//
//        memberRepository.save(member0);
//        memberRepository.save(member1);
//
//        //when
//        List<Member> result = memberRepository.findAll();
//
//        //then
//        assertThat(result).contains(member0, member1);
//    }
//
//    private List<Member> getMemberList() {
//        return Arrays.asList(
//                new Member("test121", "test1!", "HelloWorld1", "local", ""),
//                new Member("test1232", "test!", "HelloWorld", "local", "")
//        );
//    }
//
//    private List<Board> getStubBoardList() {
//        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
//        return Arrays.asList(
//                Board.builder().content("start content")
//                        .createdAt(timestamp).updatedAt(timestamp)
//                        .viewCnt(1).title("title").build(),
//                Board.builder().content("start 2")
//                        .createdAt(timestamp).updatedAt(timestamp)
//                        .viewCnt(10).title("title").build(),
//                Board.builder().build()
//        );
//    }
//
//}