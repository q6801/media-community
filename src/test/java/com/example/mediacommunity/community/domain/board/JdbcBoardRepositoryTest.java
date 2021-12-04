package com.example.mediacommunity.community.domain.board;


import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcBoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveAndFindById() {
        //
        Member member1 = new Member("test121", "test1!", "HelloWorld1", "local");
        memberRepository.save(member1);
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board board = new Board("start content", timestamp, timestamp, "test121", 1, "title");

        // when
        Board savedBoard = boardRepository.save(board);
        Board findBoard = boardRepository.findById(savedBoard.getId());

        System.out.println("savedBoard = " + savedBoard);
        System.out.println("findBoard = " + findBoard);
        //then
        assertThat(savedBoard).isEqualTo(findBoard);
    }

    @Test
    void findByWriterId() {
        String writerId = "test!!!!";

        Member member1 = new Member(writerId, "test1!", "HelloWorld1", "local");
        memberRepository.save(member1);

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board board1 = new Board("one", timestamp, timestamp, writerId, 1, "title");
        Board board2 = new Board("two", timestamp, timestamp, writerId, 10, "title");

        boardRepository.save(board1);
        boardRepository.save(board2);

        List<Board> writerBoards = boardRepository.findByWriterId(writerId);
        assertThat(writerBoards).contains(board1, board2);
    }

    @Test
    void findAll() {
        String writerId = "test!!!!";
        Member member1 = new Member(writerId, "test1!", "HelloWorld1", "local");
        memberRepository.save(member1);

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board board1 = new Board("one", timestamp, timestamp, writerId, 1, "title");
        Board board2 = new Board("two", timestamp, timestamp, writerId, 10, "title");

        boardRepository.save(board1);
        boardRepository.save(board2);

        List<Board> boards = boardRepository.findAll();
        assertThat(boards).contains(board1, board2);
    }
}