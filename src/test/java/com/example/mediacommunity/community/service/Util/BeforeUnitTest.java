package com.example.mediacommunity.community.service.Util;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.category.BoardCategory;
import com.example.mediacommunity.community.domain.member.Member;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class BeforeUnitTest {
    public List<Member> getMembers() {
        return members;
    }

    public List<Board> getBoards() {
        return boards;
    }

    List<Board> boards;
    List<Member> members;

    public BeforeUnitTest() {
        this.boards = getStubBoardList();
        this.members = getStubMemberList();
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
