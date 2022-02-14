package com.example.mediacommunity.community.repository.reply;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaReplyRepository implements ReplyRepository {
    private final BoardRepository boardRepository;

    @PersistenceContext
    EntityManager em;

    @Override
    public Reply saveReply(Reply reply) {
        em.persist(reply);
        return reply;
    }

    @Override
    public List<Reply> findAllReplies(Board board) {
        return board.getReplies();
    }

    @Override
    public Reply findReplyById(Long replyId) {
        Reply reply = em.find(Reply.class, replyId);
        return reply;
    }

    @Override
    public void deleteReply(Reply reply) {
        em.remove(reply);
    }
}
