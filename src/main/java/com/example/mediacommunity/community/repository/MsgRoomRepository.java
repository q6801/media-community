package com.example.mediacommunity.community.repository;

import com.example.mediacommunity.community.domain.MsgRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class MsgRoomRepository {

    @PersistenceContext
    private EntityManager em;

    public void saveMsgRoom(MsgRoom msgRoom) {
        em.persist(msgRoom);
    }

    public MsgRoom findByRoomId(UUID roomId) {
        return em.find(MsgRoom.class, roomId);
    }

    public List<MsgRoom> findAllRoom() {
        return em.createQuery("select m from MsgRoom m", MsgRoom.class)
                .getResultList();
    }


}
