package com.example.mediacommunity.community.repository.board;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.NotFoundPageException;
import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.board.BoardCategory;
import com.example.mediacommunity.community.domain.board.BoardOrderCriterion;
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
    public Board findBoardById(Long id) {
        Board board = em.find(Board.class, id);
        if (board == null) {
            throw new NotFoundPageException(ExceptionEnum.NOT_FOUND_PAGE);
        }
        return board;
    }

    @Override
    public List<Board> findByWriterId(Member member) {
        return member.getBoards();
    }

    @Override
    public List<Board> findBoards(Pagination pagination, String category, BoardOrderCriterion orderCriterion) {
        try {
            String sequence = "desc";
            String sql = "select b from Board b where b.boardCategory.id=:category " +
                    "order by b." + orderCriterion.getCode() + " " + sequence + ", b.createdAt desc";
            return em.createQuery(sql)
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
    public int getTotalBoardsNum(String category) {
        return em.createQuery("select count(b) from Board b where b.boardCategory.id=:category", Long.class)
                .setParameter("category", category)
                .getSingleResult().intValue();
    }

    public void saveCategory(BoardCategory bc) {
        em.persist(bc);
    }

    @Override
    public List<String> findAllCategories() {
        return em.createQuery("select c from BoardCategory c", BoardCategory.class)
                .getResultList().stream().map(BoardCategory::getId).collect(Collectors.toList());
    }

    @Override
    public BoardCategory findCategory(String categoryId) {
        return em.find(BoardCategory.class, categoryId);
    }
}
