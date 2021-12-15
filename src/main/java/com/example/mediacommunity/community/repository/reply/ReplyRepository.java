package com.example.mediacommunity.community.repository.reply;

import com.example.mediacommunity.community.domain.reply.Reply;

import java.util.List;

public interface ReplyRepository {
    Reply saveReply(Reply reply);
    List<Reply> findAllReplies(Long boardId);
}
