package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.heart.HeartDto;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.heart.HeartRepository;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.heart.HeartServiceImpl;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class HeartServiceImplTest {
    @Mock
    HeartRepository heartRepository;
    @Mock
    BoardService boardService;
    @Mock
    MemberService memberService;

    @InjectMocks
    HeartServiceImpl heartService;

    @Test
    void findTheHeart() {
        //given
        Member member = getStubMemberList().get(0);
        Board board = getStubBoardList().get(0);
        Heart pushedHeart = getStubHearts().get(0);

        given(heartRepository.findTheHeart(board, member))
                .willReturn(Optional.of(pushedHeart));
        given(boardService.findBoardById(board.getId())).willReturn(board);
        given(memberService.findMemberById(member.getLoginId())).willReturn(member);

        //when
        Optional<Heart> foundHeart = heartService.findTheHeart(board.getId(), member.getLoginId());

        //then
        assertThat(pushedHeart).isEqualTo(foundHeart.get());
    }

    @Test
    @DisplayName("좋아요가 있는 상태에서 좋아요 toggle 성공")
    void successToToggleTheHeart0() {
        //given
        Member member = getStubMemberList().get(0);
        Board board = getStubBoardList().get(0);
        Heart pushedHeart = getStubHearts().get(0);

        given(heartRepository.findTheHeart(board, member))
                .willReturn(Optional.of(pushedHeart));
        given(boardService.findBoardById(board.getId())).willReturn(board);
        given(memberService.findMemberById(member.getLoginId())).willReturn(member);

        //when
        HeartDto heartDto = heartService.toggleTheHeart(board.getId(), member.getLoginId());

        //then
        assertThat(heartDto.getPushed()).isEqualTo(false);
    }

    @Test
    @DisplayName("좋아요가 없는 상태에서 좋아요 toggle 성공")
    void successToToggleTheHeart1() {
        //given
        Member member = getStubMemberList().get(1);
        Board board = getStubBoardList().get(1);

        given(heartRepository.findTheHeart(board, member))
                .willReturn(Optional.empty());
        given(boardService.findBoardById(board.getId())).willReturn(board);
        given(memberService.findMemberById(member.getLoginId())).willReturn(member);

        //when
        HeartDto heartDto = heartService.toggleTheHeart(board.getId(), member.getLoginId());

        //then
        assertThat(heartDto.getPushed()).isEqualTo(true);
    }

    @Test
    public void findLikingBoards() {
        //given
        Member member = getStubMemberList().get(0);
        String memberId = member.getLoginId();

        given(memberService.findMemberById(memberId)).willReturn(member);
        given(heartRepository.findLikingBoards(member)).willReturn(
                        getStubHearts().stream()
                        .filter(heart -> heart.getMember().equals(member))
                        .collect(Collectors.toList())
                );
        //when
        List<Heart> likingBoards = heartService.findLikingBoards(memberId);

        //then
        assertThat(likingBoards).contains(getStubHearts().get(0));
        assertThat(likingBoards.size()).isEqualTo(1);
    }

    @Test
    public void findLikingMembers() {
        //given
        Board board = getStubBoardList().get(0);
        given(boardService.findBoardById(board.getId())).willReturn(board);
        given(heartRepository.findLikingMembers(board)).willReturn(
                getStubHearts().stream()
                        .filter(heart -> heart.getBoard().equals(board))
                        .collect(Collectors.toList())
        );

        //when
        List<Heart> likingMembers = heartService.findLikingMembers(board.getId());
       //then
        assertThat(likingMembers).contains(getStubHearts().get(0));
        assertThat(likingMembers.size()).isEqualTo(1);
    }


    private List<Board> getStubBoardList() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        return Arrays.asList(
                Board.builder()
                        .content("start content")
                        .updatedAt(timestamp)
                        .title("title").build(),
                Board.builder()
                        .content("start 2")
                        .updatedAt(timestamp)
                        .title("title").build(),
                Board.builder().build()
        );
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

    private List<Heart> getStubHearts() {
        Heart heart0 = new Heart();
        heart0.setMember(getStubMemberList().get(0));
        heart0.setBoard(getStubBoardList().get(0));

        return Arrays.asList(heart0);
    }
}