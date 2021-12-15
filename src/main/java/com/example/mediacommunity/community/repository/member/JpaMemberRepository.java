package com.example.mediacommunity.community.repository.member;

import com.example.mediacommunity.community.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
public class JpaMemberRepository implements MemberRepository {
    @PersistenceContext
    EntityManager em;

//    @Override
//    public void updateImageURL(String loginId, String url) {
//        String sql = "update members set imageUrl=? where loginId=?";
//        jdbcTemplate.update(sql, url, loginId);
//    }

//    @Override
//    public void updateNickname(String loginId, String nickname) {
//        String sql = "update members set nickname=? where loginId=?";
//        jdbcTemplate.update(sql, nickname, loginId);
//    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        log.debug("member : {}", member);
        return member;
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        try {
            Member member = em.find(Member.class, loginId);
            return Optional.ofNullable(member);
        } catch(DataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Member> findByNickname(String nickname) {
        try {
            Optional<Member> member = em.createQuery("select m from Member m where m.nickname = :nickname", Member.class)
                    .setParameter("nickname", nickname)
                    .getResultList().stream().findFirst();
            return member;
        } catch(DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public void deleteMember(Member member) {
        em.remove(member);
    }
}
