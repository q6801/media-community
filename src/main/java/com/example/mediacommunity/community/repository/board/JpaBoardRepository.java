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
                    "order by b." + orderCriterion.getCode() + " " + sequence;
            if (orderCriterion == BoardOrderCriterion.HEARTS) {
                sql = "select b.id, b.member, b.boardCategory, b.content, b.viewCnt, b.title, b.anonymous, b.createdAt, b.updatedAt from Board b, Heart h where b.boardCategory.id=:category group by h.board order by count(*)";

//                sql = "(select h.board from Heart h group by h.board order by count(*))";
            }
            List resultList = em.createNativeQuery("select b.id, b.writer_id, b.board_category_id, b.content, b.view_cnt, b.title, b.anonymous, b.created_at, b.updated_at from board b join heart h\n" +
                    "on b.id=h.board_id\n" +
//                    "where b.board_category_id='community'\n" +
                    "group by h.board_id\n" +
                    "order by count(*) desc;", Board.class)
//                    .setParameter(1, category)
//                    .setFirstResult(pagination.getStartingBoardNumInPage())
//                    .setMaxResults(pagination.getOnePageBoardsNum())
                    .getResultList();
            System.out.println(resultList);
            return null;
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
