package com.example.mediacommunity.community.repository.board;

import com.example.mediacommunity.community.domain.BoardCategory;
import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.member.MemberRepository;
import com.example.mediacommunity.community.service.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
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
    public Optional<Board> findBoardById(Long id) {
        try {
            Board board = em.find(Board.class, id);
            return Optional.of(board);
        } catch(DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Board> findByWriterId(Member member) {
        return member.getBoards();
    }

    @Override
    public List<Board> findBoards(Pagination pagination, String category) {
        try {
            return em.createQuery("select b from Board b  where b.boardCategory.id=:category order by b.updatedAt desc", Board.class)
                    .setParameter("category", category)
                    .setFirstResult(pagination.getStartingBoardNumInPage())
                    .setMaxResults(pagination.getOnePageBoardsNum())
                    .getResultList();
        } catch(DataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Board> findAll() {
        try {
            return em.createQuery("select b from Board b ", Board.class)
                    .getResultList();
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
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
    public void delete(Board board) {
        em.remove(board);
    }

    @Override
    public int getTotalBoardsNum() {
        return em.createQuery("select count(b) from Board b", Long.class)
                .getSingleResult().intValue();
    }

    public void saveCategory(BoardCategory bc) {
        em.persist(bc);
    }

    @Override
    public List<String> findAllCategories() {
        return em.createQuery("select c from BoardCategory c", BoardCategory.class)
                .getResultList().stream().map(c -> c.getId()).collect(Collectors.toList());
    }

    @Override
    public BoardCategory findCategory(String categoryId) {
        return em.find(BoardCategory.class, categoryId);
    }
}
