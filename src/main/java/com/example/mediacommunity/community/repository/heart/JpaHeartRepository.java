package com.example.mediacommunity.community.repository.heart;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaHeartRepository implements HeartRepository{

    @PersistenceContext
    EntityManager em;

    @Override
    public Optional<Heart> findTheHeart(Board board, Member member) {
        try {
            return board.getHearts().stream()
                    .filter((heart) -> heart.getMember().equals(member)).findFirst();
        } catch(DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Heart addHeart(Heart heart) {
        em.persist(heart);
        return heart;
    }

    @Override
    public List<Heart> findLikingBoards(Member member) {
        return member.getHearts();
    }

    @Override
    public List<Heart> findLikingMembers(Board board) {
        return board.getHearts();
    }

    @Override
    public Long cntHearts(Board board) {
        return Long.valueOf(findLikingMembers(board).size());
    }

    @Override
    public void deleteHeart(Heart heart) {
        em.remove(heart);
    }
}
