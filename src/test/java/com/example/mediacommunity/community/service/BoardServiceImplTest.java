package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardAddingDto;
import com.example.mediacommunity.community.domain.board.BoardCategory;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import com.example.mediacommunity.community.service.board.BoardCategoryService;
import com.example.mediacommunity.community.service.board.BoardServiceImpl;
import com.example.mediacommunity.community.service.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private BoardCategoryService boardCategoryService;

    @InjectMocks
    private BoardServiceImpl boardService;

    @Test
    void findBoard() {
        //given
        Board board0 = getStubBoardList().get(0);
        given(boardRepository.findBoardById(board0.getId())).willReturn(board0);

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
        Member writer = board0.getMember();
        BoardAddingDto board0Alpha = new BoardAddingDto("title", updatedContent, "community", false);
        given(boardRepository.findBoardById(board0.getId()))
                .willReturn(board0);
        given(memberService.findMemberById(writer.getLoginId())).willReturn(writer);
        given(boardCategoryService.findById("community")).willReturn(board0.getBoardCategory());

        //when
        boardService.modifyBoardUsingDto(board0.getId(), board0Alpha, writer.getLoginId());
        Board modifiedBoard = boardRepository.findBoardById(board0.getId());

        //then
        assertThat(updatedContent).isEqualTo(modifiedBoard.getContent());
        assertThat(updatedContent).isEqualTo(board0.getContent());
    }



    private List<Member> getStubMemberList() {
        return Arrays.asList(
                Member.builder()
                        .loginId("test121")
                        .imageUrl("")
                        .nickname("test1!")
                        .password("password0").build(),
                Member.builder()
                        .loginId("test1232")
                        .imageUrl("")
                        .nickname("test!")
                        .password("password1").build()
        );
    }

    private List<Board> getStubBoardList() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));

        BoardCategory bc = new BoardCategory("community");

        Board board0 = Board.builder().content("start content")
                .createdAt(timestamp).updatedAt(timestamp).viewCnt(1).title("title").build();
        Board board1 = Board.builder().content("start 2")
                .createdAt(timestamp).updatedAt(timestamp).viewCnt(10).title("title").build();

        board0.setMember(getStubMemberList().get(0));
        board1.setMember(getStubMemberList().get(1));
        board0.setCategory(bc);
        board1.setCategory(bc);

        return Arrays.asList(
                board0, board1
        );
    }

}