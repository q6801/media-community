package com.example.mediacommunity.community.service.reply;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.domain.reply.ReplyRequestDto;
import com.example.mediacommunity.community.repository.reply.ReplyRepository;
import com.example.mediacommunity.community.service.board.BoardService;
import com.example.mediacommunity.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardService boardService;
    private final MemberService memberService;

    @Override
    public List<Reply> findAllReplies(Long boardId) {
        Board board = boardService.findBoardById(boardId);
        return replyRepository.findAllReplies(board);
    }

    @Override
    @Transactional
    public Reply reply(Long boardId, String memberId, String content) {
        Board board = boardService.findBoardById(boardId);
        Member member = memberService.findMemberById(memberId);
        Reply reply = Reply.createReply(member, board, content);

        replyRepository.saveReply(reply);
        return reply;
    }

    @Override
    @Transactional
    public void deleteReply(String memberId, Long replyId) {
        Reply reply = replyRepository.findReplyById(replyId);
        compareUserAndWriter(memberId, reply);
        replyRepository.deleteReply(reply);
    }

    @Override
    @Transactional
    public Reply modifyReply(Long replyId, ReplyRequestDto replyDto, String memberId) {
        Reply reply = replyRepository.findReplyById(replyId);

        compareUserAndWriter(memberId, reply);
        reply.updateReplyWithDto(replyDto);
        replyRepository.saveReply(reply);
        return reply;
    }

    private void compareUserAndWriter(String memberId, Reply reply) {
        if (reply.getMember().getLoginId().equals(memberId)) {
            return;
        }
        throw new CustomRuntimeException(ExceptionEnum.NOT_ALLOWED_ACCESS);
    }
}
