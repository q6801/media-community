package com.example.mediacommunity.community.service.board;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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
        given(boardRepository.findBoardById(board0.getId())).willReturn(Optional.of(board0));

        //when
        Board foundBoard = boardService.findBoardById(board0.getId());

        //then
        assertThat(foundBoard).isEqualTo(board0);

    }

//    @Test
//    void findBoards() {
//        //given
//        Board board0 = getStubBoardList().get(0);
//        Board board1 = getStubBoardList().get(0);
//        String boardWriter = board0.getWriterId();
//        List<Board> foundBoards = getStubBoardList().stream().filter(
//                board -> boardWriter.equals(board.getWriterId()))
//                .collect(Collectors.toList());
//        given(boardRepository.findByWriterId(board0.getWriterId())).willReturn(foundBoards);
//
//        //when
//        List<Board> boards = boardService.findBoards(boardWriter);
//
//        //then
//        assertThat(boards).contains(board0, board1);
//    }

    @Test
    void findAllBoards() {
        //given
        Board board0 = getStubBoardList().get(0);
        Board board1 = getStubBoardList().get(1);
        given(boardRepository.findAll()).willReturn(getStubBoardList());
        assertThat(getStubBoardList()).contains(board0, board1);

        //when
        List<Board> boards = boardService.findAllBoards();

        //then
        System.out.println("boards = " + boards);
        System.out.println("board0 = " + board0);
        System.out.println("board1 = " + board1);
        assertThat(boards).contains(board0, board1);
    }

//    @Test
//    void modifyBoardUsingDto() {
//        //given
//        Board board0 = getStubBoardList().get(0);
//        Board board0Alpha = getStubBoardList().get(1);
//        given(boardRepository.findById(board0.getId())).willReturn(board0Alpha);
//
//        //when
//        boardService.modifyBoardUsingDto(board0.getId(), board0Alpha);
//        Board modifiedBoard = boardRepository.findById(board0.getId());
//
//        //then
//        assertThat(board0).isNotEqualTo(modifiedBoard);
//        assertThat(board0.getContent()).isNotEqualTo(modifiedBoard.getContent());
//    }


    private List<Board> getStubBoardList() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        return Arrays.asList(
                Board.builder().content("start content")
                        .createdAt(timestamp).updatedAt(timestamp)
                        .viewCnt(1).title("title").build(),
                Board.builder().content("start 2")
                        .createdAt(timestamp).updatedAt(timestamp)
                        .viewCnt(10).title("title").build(),
                Board.builder().build()
        );
    }

}