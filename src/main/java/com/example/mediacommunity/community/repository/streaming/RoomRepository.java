package com.example.mediacommunity.community.repository.streaming;

import com.example.mediacommunity.community.domain.chat.Room;
import com.example.mediacommunity.community.domain.chat.RoomType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RoomRepository {

    @PersistenceContext
    private EntityManager em;

    public void saveRoom(Room room) {
        em.persist(room);
    }

    public Room findByRoomId(UUID roomId) {
        return em.find(Room.class, roomId);
    }

    public Optional<Room> findByPresenter(String username, RoomType roomType) {
        return em.createQuery(
                "select s from Room s where s.roomType=:roomType and s.presenter=:username", Room.class)
                .setParameter("username", username)
                .setParameter("roomType", roomType)
                .getResultList().stream().findFirst();
    }

    public List<Room> findRoomsByType(RoomType roomType) {
        return em.createQuery("select s from Room s where s.roomType=:roomType", Room.class)
                .setParameter("roomType", roomType)
                .getResultList();
    }


    public void deleteRoom(Room room) {
        em.remove(room);
    }


}
