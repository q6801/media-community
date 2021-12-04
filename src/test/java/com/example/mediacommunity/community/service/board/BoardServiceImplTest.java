package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceImplTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardService boardService;

    @Test
    void save() {
        String writerId = "test!!!!";

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board board = new Board("one", timestamp, timestamp, writerId, 1, "title");
        assertThrows(RuntimeException.class, () -> {
            boardService.save(board);
        });
    }

    @Test
    void findBoard() {
        //given
        String writerId = "test!!!!";

        Member member1 = new Member(writerId, "test1!", "HelloWorld1", "local");
        memberRepository.save(member1);
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board savedBoard = boardService.save(
                new Board("one", timestamp, timestamp, writerId, 1, "title"));

        //when
        Optional<Board> foundBoard = boardService.findBoard(savedBoard.getId());

        //then
        Assertions.assertThat(foundBoard.get()).isEqualTo(savedBoard);

    }

    @Test
    void failToFindBoard() {
        //given
        long notSavedBoardId = 123214;

        //when
        Optional<Board> foundBoard = boardService.findBoard(notSavedBoardId);

        //then
        Assertions.assertThat(foundBoard.isEmpty()).isEqualTo(true);

    }

    @Test
    void findBoards() {
        //given
        String writerId = "test!!!!";

        Member member1 = new Member(writerId, "test1!", "HelloWorld1", "local");
        memberRepository.save(member1);
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board savedBoard1 = boardService.save(
                new Board("one", timestamp, timestamp, writerId, 1, "title"));
        Board savedBoard2 = boardService.save(
                new Board("one", timestamp, timestamp, writerId, 1, "title"));

        //when
        List<Board> boards = boardService.findBoards(writerId);

        //then
        Assertions.assertThat(boards).contains(savedBoard1, savedBoard2);
    }

    @Test
    void findAllBoards() {
        //given
        String writerId = "test!!!!";

        Member member1 = new Member(writerId, "test1!", "HelloWorld1", "local");
        memberRepository.save(member1);
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board savedBoard1 = boardService.save(
                new Board("one", timestamp, timestamp, writerId, 1, "title"));
        Board savedBoard2 = boardService.save(
                new Board("one", timestamp, timestamp, writerId, 1, "title"));

        //when
        List<Board> boards = boardService.findAllBoards();

        //then
        Assertions.assertThat(boards).contains(savedBoard1, savedBoard2);
    }

    @Test
    void modifyBoard() {
        //given
        String writerId = "test!!!!";

        Member member1 = new Member(writerId, "test1!", "HelloWorld1", "local");
        memberRepository.save(member1);
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board savedBoard = boardService.save(
                new Board("one", timestamp, timestamp, writerId, 1, "title"));

        //when
        boardService.modifyBoard(savedBoard.getId(), new Board("hello world!", timestamp, timestamp, writerId, 1, "title"));
        Optional<Board> modifiedBoard = boardService.findBoard(savedBoard.getId());

        //then
        Assertions.assertThat(savedBoard).isNotEqualTo(modifiedBoard.get());
        Assertions.assertThat(savedBoard.getContent()).isNotEqualTo(modifiedBoard.get().getContent());
    }
}