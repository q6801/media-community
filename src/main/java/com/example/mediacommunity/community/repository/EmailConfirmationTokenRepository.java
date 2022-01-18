package com.example.mediacommunity.community.repository;

import com.example.mediacommunity.community.domain.email.EmailConfirmationToken;
import com.example.mediacommunity.community.domain.member.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EmailConfirmationTokenRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<EmailConfirmationToken> findRecentMail(Member member) {
        return member.getEmailTokens().stream().findFirst();
    }

    public void save(EmailConfirmationToken emailToken) {
        em.persist(emailToken);
    }

    public void delete(EmailConfirmationToken emailToken) {
        em.remove(emailToken);
    }
}
