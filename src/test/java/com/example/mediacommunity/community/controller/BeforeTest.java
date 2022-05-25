package com.example.mediacommunity.community.controller;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardRequestDto;
import com.example.mediacommunity.community.domain.category.BoardCategory;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.SignUpDto;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.service.board.BoardCategoryService;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import com.example.mediacommunity.community.service.reply.ReplyService;

public class BeforeTest {
     protected long createBoard(BoardService boardService, String categoryName, Member member) {
        Board board = Board.convertBoardRequestDtoToBoard(
                new BoardRequestDto("newTitle", "newContent", categoryName, false),
                member,
                new BoardCategory(categoryName)
        );
        Board savedBoard = boardService.save(board);
        return savedBoard.getId();
    }

    protected void createCategory(BoardCategoryService boardCategoryService, String categoryName) {
        BoardCategory bc = new BoardCategory(categoryName);
        boardCategoryService.save(bc);
    }

    protected Member saveMember(MemberService memberService) {
        return memberService.encodeAndSave(new SignUpDto("tester0", "password", "password","tester0"));
    }

    protected Reply createReply(ReplyService replyService, long boardId, String memberId) {
        return replyService.reply(boardId, memberId, "newContent");
    }
}
