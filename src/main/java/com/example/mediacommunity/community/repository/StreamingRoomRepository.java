package com.example.mediacommunity.community.repository;

import com.example.mediacommunity.community.domain.chat.StreamingRoom;
import com.example.mediacommunity.community.domain.member.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class StreamingRoomRepository {

    @PersistenceContext
    private EntityManager em;

    public void saveRoom(StreamingRoom streamingRoom) {
        em.persist(streamingRoom);
    }

    public StreamingRoom findByRoomId(UUID roomId) {
        return em.find(StreamingRoom.class, roomId);
    }

    public Optional<StreamingRoom> findByPresenter(String username) {
        return em.createQuery(
                "select s from StreamingRoom s where s.presenter=:username", StreamingRoom.class)
                .setParameter("username", username)
                .getResultList().stream().findFirst();
    }

    public List<StreamingRoom> findAllRoom() {
        return em.createQuery("select s from StreamingRoom s", StreamingRoom.class)
                .getResultList();
    }


    public void deleteRoom(StreamingRoom streamingRoom) {
        em.remove(streamingRoom);
    }


}
