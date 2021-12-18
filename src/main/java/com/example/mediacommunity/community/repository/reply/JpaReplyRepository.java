package com.example.mediacommunity.community.repository.reply;

import com.example.mediacommunity.community.domain.reply.Reply;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class JpaReplyRepository implements ReplyRepository{
    @PersistenceContext
    EntityManager em;

    @Override
    public Reply saveReply(Reply reply) {
        em.persist(reply);
        return reply;
    }

    @Override
    public List<Reply> findAllReplies(Long boardId) {
        return em.createQuery("select r from Reply r where r.boardId=:boardId", Reply.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }
}
