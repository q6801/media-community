package com.example.mediacommunity.community.repository.heart;

import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.repository.board.BoardRepository;
import com.example.mediacommunity.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaHeartRepository implements HeartRepository{
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Override
    public Optional<Heart> findTheHeart(Long boardId, String memberId) {
        Board board = boardRepository.findById(boardId);
        Member member = memberRepository.findByLoginId(memberId).orElseThrow();
        return board.getHearts().stream().filter((heart) -> heart.getMember().equals(member)).findFirst();
    }

    @Override
    public Heart addHeart(Heart heart) {
        em.persist(heart);
        return heart;
    }

    @Override
    public List<Heart> findLikingBoards(String memberId) {
        Member member = memberRepository.findByLoginId(memberId).orElseThrow();
        return member.getHearts();
    }

    @Override
    public List<Heart> findLikingMembers(Long boardId) {
        Board board = boardRepository.findById(boardId);
        return board.getHearts();
    }

    @Override
    public Long cntHearts(Long boardId) {
        return Long.valueOf(findLikingMembers(boardId).size());
    }

    @Override
    public void deleteHeart(Long id) {
        Heart heart = em.find(Heart.class, id);
        em.remove(heart);
    }
}
