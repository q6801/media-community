package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.chat.Room;
import com.example.mediacommunity.community.domain.chat.RoomType;
import com.example.mediacommunity.community.repository.streaming.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Transactional
@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomServiceImpl;


    private UUID id0 = UUID.randomUUID();
    private UUID id1 = UUID.randomUUID();

    @Test
    public void createRoom() {
        // given
        Room room = getStubRoomList().get(1);

        //when
        Room createdRoom = roomServiceImpl
                .createRoom(room.getRoomName(), room.getPresenter(), RoomType.STREAMING);

        //then
        assertThat(room).isEqualTo(createdRoom);
    }

    @Test
    public void findById() {
        //given
        Room room = getStubRoomList().get(0);
        given(roomRepository.findByRoomId(room.getId())).willReturn(room);

        //when
        Room foundRoom = roomServiceImpl.findById(room.getId());

        //then
        assertThat(foundRoom).isEqualTo(room);
    }

    @Test
    public void findByPresenter() {
        //given
        Room room = getStubRoomList().get(0);
        given(roomRepository.findByPresenter(room.getPresenter(), RoomType.STREAMING))
                .willReturn(Optional.of(room));

        //when
        Optional<Room> foundRoom = roomServiceImpl.findByPresenter(room.getPresenter(), RoomType.STREAMING);

        //then
        assertThat(foundRoom.get()).isEqualTo(room);
    }

    @Test
    public void findAllRoom() {
        //given
        given(roomRepository.findRoomsByType(RoomType.STREAMING)).willReturn(getStubRoomList());

        //when
        List<Room> allRoom = roomServiceImpl.findRoomsByType(RoomType.STREAMING);

        //then
        System.out.println(getStubRoomList().get(0));
        System.out.println(allRoom);
        assertThat(allRoom).contains(getStubRoomList().get(0));
        assertThat(allRoom.size()).isEqualTo(2);
    }



    private List<Room> getStubRoomList() {

        return Arrays.asList(
                Room.builder()
                        .roomName("room0")
                        .id(id0)
                        .presenter("tester0")
                        .roomType(RoomType.STREAMING)
                        .build(),
                Room.builder()
                        .roomName("room1")
                        .presenter("tester1")
                        .roomType(RoomType.STREAMING)
                        .build()
        );
    }

}
