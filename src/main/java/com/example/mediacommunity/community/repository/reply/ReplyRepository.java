package com.example.mediacommunity.community.repository.reply;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.reply.Reply;

import java.util.List;

public interface ReplyRepository {
    Reply saveReply(Reply reply);
    List<Reply> findAllReplies(Board board);
    Reply findReplyById(Long replyId);
    void deleteReply(Reply reply);
}
