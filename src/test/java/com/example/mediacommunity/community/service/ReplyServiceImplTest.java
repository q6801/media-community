package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.repository.reply.ReplyRepository;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.reply.ReplyServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ReplyServiceImplTest {
    @Mock
    ReplyRepository replyRepository;
    @Mock
    BoardService boardService;

    @InjectMocks
    ReplyServiceImpl replyService;

    @Test
    void findALlReplies() {
        //given
        Board board = getStubBoardList().get(0);
        Member member = getStubMemberList().get(0);
        Reply reply = getStubReplies().get(0);
        reply.setReplyer(member);
        reply.setBoard(board);

        BDDMockito.given(replyRepository.findAllReplies(board)).willReturn(Arrays.asList(reply));
        BDDMockito.given(boardService.findBoardById(board.getId())).willReturn(board);

        //when
        List<Reply> allReplies = replyService.findAllReplies(board.getId());

        //then
        Assertions.assertThat(allReplies.size()).isEqualTo(1);
    }

    private List<Board> getStubBoardList() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        return Arrays.asList(
                Board.builder().content("start content")
                        .updatedAt(timestamp)
                        .title("title").build(),
                Board.builder().content("start 2")
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

    private List<Reply> getStubReplies() {
        Reply reply0 = new Reply("hello world!!");
        Reply reply1 = new Reply("hello world12!!");
        return Arrays.asList(
                reply0, reply1
        );
    }

}