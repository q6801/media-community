package com.example.mediacommunity.community.repository.reply;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
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
    public List<Reply> findAllReplies(Long boardId) {
        Board board = boardRepository.findById(boardId);
        return board.getReplies();
    }
}
