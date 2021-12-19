package com.example.mediacommunity.community.service.reply;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.repository.reply.ReplyRepository;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardService boardService;
    private final MemberService memberService;

    @Override
    public Reply saveReply(Reply reply) {
        return replyRepository.saveReply(reply);
    }

    @Override
    public List<Reply> findAllReplies(Long boardId) {
        return replyRepository.findAllReplies(boardId);
    }

    @Override
    public void reply(Long boardId, String memberId, String content) {
        Board board = boardService.findBoard(boardId)
                .orElseThrow(() -> new RuntimeException("board 없음"));
        Member member = memberService.findMemberById(memberId);
        Reply reply = Reply.createReply(member, board, content);
        replyRepository.saveReply(reply);
    }
}
