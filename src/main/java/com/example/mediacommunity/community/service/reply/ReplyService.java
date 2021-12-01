package com.example.mediacommunity.community.service.reply;

import com.example.mediacommunity.community.domain.reply.Reply;

import java.util.List;


public interface ReplyService {
    Reply saveReply(Reply reply);
    List<Reply> findAllReplies(Long boardId);
}
