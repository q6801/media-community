package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardOrderCriterion;
import com.example.mediacommunity.community.domain.board.BoardRequestDto;
import com.example.mediacommunity.community.domain.category.BoardCategoriesDto;
import com.example.mediacommunity.community.domain.category.BoardCategory;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import com.example.mediacommunity.community.service.board.BoardCategoryService;
import com.example.mediacommunity.community.service.board.BoardServiceImpl;
import com.example.mediacommunity.community.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @DisplayName("board 조회 성공")
    void findBoard() {
        //given
        Board board0 = getStubBoardList().get(0);
        given(boardRepository.findBoardById(board0.getId())).willReturn(board0);

        //when
        Board foundBoard = boardService.findBoardById(board0.getId());

        //then
        assertTrue(board0.equals(foundBoard));
        assertFalse(foundBoard.equals(null));
        assertThat(foundBoard).isEqualTo(board0);
    }

    @Test
    @DisplayName("빈 boards 조회 성공")
    void successfindBoards() {
        //given
        Pagination pagination = new Pagination();
        String category = "community";
        given(boardRepository.findBoards(pagination,
                category, BoardOrderCriterion.CREATED))
                .willReturn(null);
        //when
        List<Board> boards = boardService.findBoards(
                pagination, category, BoardOrderCriterion.CREATED);
        //then
        assertThat(boards).isEmpty();
    }


    @Test
    @DisplayName("특정 사용자의 boards 조회 성공")
    void findByWriterId() {
        //given
        Member member0 = getStubMemberList().get(0);
        List<Board> boardsWrittenByMember0 = getStubBoardList().stream().filter(board -> {
            if (board.getMember().equals(member0)) return true;
            return false;
        }).collect(Collectors.toList());

        given(boardRepository.findByWriterId(member0))
                .willReturn(boardsWrittenByMember0);
        given(memberService.findMemberById(member0.getLoginId()))
                .willReturn(member0);
        //when
        List<Board> boards = boardService.findByWriterId(member0.getLoginId());
        //then
        assertThat(boards).containsAll(boardsWrittenByMember0).
                isEqualTo(boardsWrittenByMember0);
    }

    @Test
    @DisplayName("게시물 수정 성공")
    void successModifyBoard() {
        //given
        String updatedContent = "updated content";
        Board board0 = getStubBoardList().get(0);
        Member writer = board0.getMember();
        BoardRequestDto board0Alpha = new BoardRequestDto("title", updatedContent, "community", false);

        given(boardRepository.findBoardById(board0.getId())).willReturn(board0);
        given(memberService.findMemberById(writer.getLoginId())).willReturn(writer);
        given(boardCategoryService.findById("community")).willReturn(board0.getBoardCategory());
        //when
        boolean result = boardService.modifyBoardUsingDto(board0.getId(), board0Alpha, writer.getLoginId());
        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("게시물 수정 성공")
    void failToModifyBoard() {
        //given
        String updatedContent = "updated content";
        Board board0 = getStubBoardList().get(0);
        Member writer = getStubMemberList().get(1);
        BoardRequestDto board0Alpha = new BoardRequestDto("title", updatedContent, "community", false);

        given(boardRepository.findBoardById(board0.getId())).willReturn(board0);
        given(memberService.findMemberById(writer.getLoginId())).willReturn(writer);
        given(boardCategoryService.findById("community")).willReturn(board0.getBoardCategory());
        //when
        boolean result = boardService.modifyBoardUsingDto(board0.getId(), board0Alpha, writer.getLoginId());
        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("모든 카테고리 조회 성공")
    void successToFindAllCategories() {
        List<String> categories = Arrays.asList("comm", "qna");
        given(boardRepository.findAllCategories())
                .willReturn(categories);

        BoardCategoriesDto rtVal = boardService.findAllCategories();

        categories.stream()
                .forEach(category ->
                        assertThat(rtVal.getCategories()).contains(category));
        assertThat(categories).isEqualTo(rtVal.getCategories());
    }

    @Test
    @DisplayName("board 삭제 성공")
    void successToDeleteBoard() {
        Board board0 = getStubBoardList().get(0);
        Member writer = board0.getMember();
        given(boardRepository.findBoardById(board0.getId())).willReturn(board0);
        given(memberService.findMemberById(writer.getLoginId())).willReturn(writer);
//        given(boardRepository.delete(board0))

        boolean result = boardService.deleteBoard(board0.getId(), writer.getLoginId());
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("board 삭제 실패")
    void failToDeleteBoard() {
        Board board0 = getStubBoardList().get(0);
        Member writer = getStubMemberList().get(1);
        given(boardRepository.findBoardById(board0.getId())).willReturn(board0);
        given(memberService.findMemberById(writer.getLoginId())).willReturn(writer);
//        given(boardRepository.delete(board0))

        boolean result = boardService.deleteBoard(board0.getId(), writer.getLoginId());
        assertThat(result).isFalse();
    }



    private List<Member> getStubMemberList() {
        return Arrays.asList(
                Member.builder()
                        .loginId("test121")
                        .nickname("test1!")
                        .password("password0").build(),
                Member.builder()
                        .loginId("test1232")
                        .nickname("test!")
                        .password("password1").build()
        );
    }

    private List<Board> getStubBoardList() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));

        BoardCategory bc = new BoardCategory("community");

        Board board0 = Board.builder().content("start content")
                .updatedAt(timestamp).title("title").build();
        Board board1 = Board.builder().content("start 2")
                .updatedAt(timestamp).title("title").build();

        board0.setMember(getStubMemberList().get(0));
        board1.setMember(getStubMemberList().get(1));
        board0.setBoardCategory(bc);
        board1.setBoardCategory(bc);

        return Arrays.asList(
                board0, board1
        );
    }

}