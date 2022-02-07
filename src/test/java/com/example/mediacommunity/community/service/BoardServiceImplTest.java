package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardAddingDto;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import com.example.mediacommunity.community.service.board.BoardServiceImpl;
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

    @Test
    void modifyBoardUsingDto() {
        //given
        String updatedContent = "updated content";
        Board board0 = getStubBoardList().get(0);
        BoardAddingDto board0Alpha = new BoardAddingDto("title", updatedContent);
        given(boardRepository.findBoardById(board0.getId()))
                .willReturn(Optional.of(board0));

        //when
        boardService.modifyBoardUsingDto(board0.getId(), board0Alpha);
        Board modifiedBoard = boardRepository.findBoardById(board0.getId()).get();

        //then
        assertThat(updatedContent).isEqualTo(modifiedBoard.getContent());
        assertThat(updatedContent).isEqualTo(board0.getContent());
    }


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