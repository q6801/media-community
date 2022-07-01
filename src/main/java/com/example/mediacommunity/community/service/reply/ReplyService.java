package com.example.mediacommunity.community.service.reply;

import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.domain.reply.ReplyRequestDto;

import java.util.List;


public interface ReplyService {
    List<Reply> findAllReplies(Long boardId);
    Reply reply(Long boardId, String memberId, String content);
    void deleteReply(String memberId, Long replyId);
    Reply modifyReply(Long replyId, ReplyRequestDto replyDto, String memberId);
}
