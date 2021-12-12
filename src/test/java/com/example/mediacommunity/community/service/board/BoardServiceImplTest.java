package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardRepository;
import com.example.mediacommunity.community.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {
    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardServiceImpl boardService;

    @Test
    void findBoard() {
        //given
        Board board0 = getStubBoardList().get(0);
        given(boardRepository.findById(board0.getId())).willReturn(board0);

        //when
        Optional<Board> foundBoard = boardService.findBoard(board0.getId());

        //then
        Assertions.assertThat(foundBoard.get()).isEqualTo(board0);

    }

    @Test
    void findBoards() {
        //given
        Board board0 = getStubBoardList().get(0);
        Board board1 = getStubBoardList().get(0);
        String boardWriter = board0.getWriterId();
        List<Board> foundBoards = getStubBoardList().stream().filter(
                board -> boardWriter.equals(board.getWriterId()))
                .collect(Collectors.toList());
        given(boardRepository.findByWriterId(board0.getWriterId())).willReturn(foundBoards);

        //when
        List<Board> boards = boardService.findBoards(boardWriter);

        //then
        Assertions.assertThat(boards).contains(board0, board1);
    }

    @Test
    void findAllBoards() {
        //given
        Board board0 = getStubBoardList().get(0);
        Board board1 = getStubBoardList().get(0);

        List<Board> foundBoards = getStubBoardList();
        given(boardRepository.findAll()).willReturn(foundBoards);

        //when
        List<Board> boards = boardService.findAllBoards();

        //then
        Assertions.assertThat(boards).contains(board0, board1);
    }

    @Test
    void modifyBoard() {
        //given
        Board board0 = getStubBoardList().get(0);
        Board board0Alpha = getStubBoardList().get(1);
        given(boardRepository.findById(board0.getId())).willReturn(board0Alpha);

        //when
        boardService.modifyBoard(board0.getId(), board0Alpha);
        Board modifiedBoard = boardRepository.findById(board0.getId());

        //then
        Assertions.assertThat(board0).isNotEqualTo(modifiedBoard);
        Assertions.assertThat(board0.getContent()).isNotEqualTo(modifiedBoard.getContent());
    }

    private List<Member> getStubMemberList() {
        return Collections.singletonList(
                new Member("test121", "test1!", "HelloWorld1", "local", "")
        );
    }

    private List<Board> getStubBoardList() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        return Arrays.asList(
                new Board("start content", timestamp, timestamp, "test121", 1, "title"),
                new Board("two", timestamp, timestamp, "test121", 10, "title")
        );
    }
}