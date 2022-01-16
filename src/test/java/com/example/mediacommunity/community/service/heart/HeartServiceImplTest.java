package com.example.mediacommunity.community.service.heart;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.heart.HeartRepository;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
        Board board = getStubBoardList().get(0);
        Member member = getStubMemberList().get(0);
        Heart heart = getStubHearts().get(0);

        given(heartRepository.findTheHeart(board, member)).willReturn(Optional.of(heart));

        //when
        Optional<Heart> foundHeart = heartService.findTheHeart(board.getId(), member.getLoginId());

        //then
        assertThat(heart).isEqualTo(foundHeart.get());
    }

    @Test
    void toggleTheHeart() {
        //given
        Member member = getStubMemberList().get(0);
        Board board = getStubBoardList().get(0);
        Heart pushedHeart = getStubHearts().get(0);

        given(heartRepository.findTheHeart(board, member))
                .willReturn(Optional.of(pushedHeart));
        given(boardService.findBoardById(board.getId())).willReturn(board);
        given(memberService.findMemberById(member.getLoginId())).willReturn(member);

        //when
        Boolean pushed = heartService.toggleTheHeart(board.getId(), member.getLoginId());

        //then
        assertThat(pushed).isEqualTo(false);
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

    private List<Heart> getStubHearts() {
        Heart heart0 = Heart.builder().build();
        Heart heart1 = Heart.builder().build();

        heart0.setMember(getStubMemberList().get(0));
        heart0.setBoard(getStubBoardList().get(0));

        heart1.setMember(getStubMemberList().get(1));
        heart1.setBoard(getStubBoardList().get(1));

        return Arrays.asList(heart0, heart1);
    }
}