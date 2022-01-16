//package com.example.mediacommunity.community.domain.board;
//
//
//import com.example.mediacommunity.community.domain.member.Member;
//import com.example.mediacommunity.community.repository.member.MemberRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class JdbcBoardRepositoryTest {
//    @Autowired
//    BoardRepository boardRepository;
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    void saveAndFindById() {
//        // given
//        Member member = getMemberList().get(0);
//        Board board = getBoardList().get(0);
//
//        // when
//        memberRepository.save(member);
//        Board savedBoard = boardRepository.save(board);
//        Board findBoardById = boardRepository.findById(savedBoard.getId());
//
//        //then
//        assertThat(savedBoard).isEqualTo(findBoardById);
//    }
//
//    @Test
//    void findByWriterIdAndFindAll() {
//        //given
//        Member member = getMemberList().get(0);
//        Board board0 = getBoardList().get(0);
//        Board board1 = getBoardList().get(1);
//
//        //when
//        memberRepository.save(member);
//        boardRepository.save(board0);
//        boardRepository.save(board1);
//
//        //then
//        List<Board> writerBoards = boardRepository.findByWriterId(board0.getWriterId());
//        assertThat(writerBoards).contains(board0, board1);
//
//        List<Board> boards = boardRepository.findAll();
//        assertThat(boards).contains(board0, board1);
//    }
//
//    private List<Member> getMemberList() {
//        return Collections.singletonList(
//                new Member("test121", "test1!", "HelloWorld1", "local", "")
//        );
//    }
//
//    private List<Board> getBoardList() {
//        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
//        return Arrays.asList(
//                new Board.Builder("start content", "test121", 1, "title")
//                        .createdAt(timestamp).updatedAt(timestamp).build(),
//                new Board.Builder("start two", "test121", 10, "title")
//                        .createdAt(timestamp).updatedAt(timestamp).build()
//        );
//    }
//}