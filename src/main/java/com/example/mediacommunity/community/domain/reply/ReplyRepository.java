package com.example.mediacommunity.community.domain.reply;

import java.util.List;

public interface ReplyRepository {
    Reply saveReply(Reply reply);
    List<Reply> findAllReplies(Long boardId);
}
