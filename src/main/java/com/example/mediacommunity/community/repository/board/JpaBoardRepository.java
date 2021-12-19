package com.example.mediacommunity.community.repository.board;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import com.example.mediacommunity.community.repository.member.MemberRepository;
import com.example.mediacommunity.community.service.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaBoardRepository implements BoardRepository {
    private final MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Override
    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    @Override
    public Board findById(Long id) {
        Board board = em.find(Board.class, id);
        return board;
    }

    @Override
    public List<Board> findByWriterId(String writerId) {
        Member member = memberRepository.findByLoginId(writerId).orElseThrow();
        return member.getBoards();
    }

    @Override
    public List<Board> findBoards(Pagination pagination) {
        return em.createQuery("select b from Board b order by b.updatedAt desc", Board.class)
                .setFirstResult(pagination.getStartingBoardNumInPage())
                .setMaxResults(pagination.getOnePageBoardsNum())
                .getResultList();
    }

    @Override
    public List<Board> findAll() {
        return em.createQuery("select b from Board b ", Board.class)
                .getResultList();
    }

    /**
     * jpa에서는 update 메서드가 필요 없다.
     * @param BoardIdx
     * @param updateParam
     */
    @Override
    public void update(Long BoardIdx, Board updateParam) {
    }

    /**
     * board 도메인 메서드로 대체
     * @param id
     * @param viewCnt
     */
    @Override
    public void increaseViewCnt(Long id, int viewCnt) {
    }

    @Override
    public void delete(Long id) {
        Board board = em.find(Board.class, id);
        em.remove(board);
    }

    @Override
    public int getTotalBoardsNum() {
        return em.createQuery("select count(b) from Board b", Long.class)
                .getSingleResult().intValue();
    }
}
