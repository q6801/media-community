package com.example.mediacommunity.community.service.reply;

import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.domain.reply.ReplyInputDto;

import java.util.List;


public interface ReplyService {
    List<Reply> findAllReplies(Long boardId);
    Reply reply(Long boardId, String memberId, String content);
    void deleteReply(Long replyId);
    Reply modifyReply(Long replyId, ReplyInputDto replyDto, String memberId);
}
