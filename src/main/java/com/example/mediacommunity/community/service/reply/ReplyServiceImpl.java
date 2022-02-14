package com.example.mediacommunity.community.service.reply;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.domain.reply.ReplyInfoDto;
import com.example.mediacommunity.community.domain.reply.ReplyInputDto;
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
        Board board = boardService.findBoardById(boardId);
        return replyRepository.findAllReplies(board);
    }

    @Override
    public Reply reply(Long boardId, String memberId, String content) {
        Board board = boardService.findBoardById(boardId);
        Member member = memberService.findMemberById(memberId);
        Reply reply = Reply.createReply(member, board, content);

        replyRepository.saveReply(reply);
        return reply;
    }

    @Override
    public void deleteReply(Long replyId) {
        Reply reply = replyRepository.findReplyById(replyId);
        replyRepository.deleteReply(reply);
    }

    @Override
    public Reply modifyReply(Long replyId, ReplyInputDto replyDto, String memberId) {
        Member member = memberService.findMemberById(memberId);
        Reply reply = replyRepository.findReplyById(replyId);

        if (compareUserAndWriter(member, reply)) {
            reply.updateReplyWithDto(replyDto);
        }

        replyRepository.saveReply(reply);
        return reply;
    }

    private boolean compareUserAndWriter(Member member, Reply reply) {
        if (member != null  && reply.getMember().equals(member)) {
            return true;
        }
        return false;
    }
}
