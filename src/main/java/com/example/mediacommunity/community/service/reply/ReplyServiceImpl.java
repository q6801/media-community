package com.example.mediacommunity.community.service.reply;

import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public Reply saveReply(Reply reply) {
        return replyRepository.saveReply(reply);
    }

    @Override
    public List<Reply> findAllReplies(Long boardId) {
        return replyRepository.findAllReplies(boardId);
    }
}
