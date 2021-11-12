package com.example.mediacommunity.domain.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcBoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void saveAndFindById() {
        //
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board board = new Board("start content", timestamp, timestamp, "test", 1);

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
        String writerId = "test";

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board board1 = new Board("one", timestamp, timestamp, writerId, 1);
        Board board2 = new Board("two", timestamp, timestamp, writerId, 10);

        boardRepository.save(board1);
        boardRepository.save(board2);

        List<Board> writerBoards = boardRepository.findByWriterId(writerId);
        assertThat(writerBoards.size()).isEqualTo(2);
        assertThat(writerBoards).contains(board1, board2);
    }

    @Test
    void findAll() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board board1 = new Board("one", timestamp, timestamp, "test", 1);
        Board board2 = new Board("two", timestamp, timestamp, "test", 10);

        boardRepository.save(board1);
        boardRepository.save(board2);

        List<Board> boards = boardRepository.findAll();
        assertThat(boards).contains(board1, board2);
    }
}