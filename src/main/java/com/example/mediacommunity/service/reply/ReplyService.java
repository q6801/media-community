package com.example.mediacommunity.service.reply;

import com.example.mediacommunity.domain.reply.Reply;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ReplyService {
    Reply saveReply(Reply reply);
    List<Reply> findAllReplies(Long boardId);
}
