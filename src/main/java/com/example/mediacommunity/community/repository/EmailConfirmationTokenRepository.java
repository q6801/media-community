package com.example.mediacommunity.community.repository;

import com.example.mediacommunity.community.domain.email.EmailConfirmationToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class EmailConfirmationTokenRepository {

    @PersistenceContext
    private EntityManager em;

    public EmailConfirmationToken findById(UUID id) {
        return em.find(EmailConfirmationToken.class, id);
    }

    public void save(EmailConfirmationToken emailToken) {
        em.persist(emailToken);
    }

    public void delete(EmailConfirmationToken emailToken) {
        em.remove(emailToken);
    }
}
