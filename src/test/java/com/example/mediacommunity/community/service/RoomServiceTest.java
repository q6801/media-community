package com.example.mediacommunity.community.service;

import com.example.mediacommunity.Exception.custom.BlankExistException;
import com.example.mediacommunity.community.domain.chat.Room;
import com.example.mediacommunity.community.domain.chat.RoomType;
import com.example.mediacommunity.community.repository.streaming.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @DisplayName("방 생성 성공")
    public void successToCreateRoom() {
        // given
        Room room = getStubRoomList().get(1);

        //when
        Room createdRoom = roomServiceImpl
                .createRoom(room.getRoomName(), room.getPresenter(), RoomType.STREAMING);

        //then
        assertThat(room).isEqualTo(createdRoom);
//        Assertions.assertTrue(room.equals(createdRoom));
//        Assertions.assertFalse(createdRoom.equals(null));
    }

    @Test
    @DisplayName("방 이름을 비워서 방 생성 실패")
    public void failToCreateRoom() {
        Room room = getStubRoomList().get(1);

        assertThatThrownBy(() -> roomServiceImpl
                .createRoom("", room.getPresenter(), RoomType.STREAMING))
                .isInstanceOf(BlankExistException.class);
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
        List<Room> roomList = getStubRoomList();
        given(roomRepository.findRoomsByType(RoomType.STREAMING)).willReturn(roomList);

        //when
        List<Room> allRoom = roomServiceImpl.findRoomsByType(RoomType.STREAMING);

        //then
        assertThat(allRoom).contains(roomList.get(0));
        assertThat(allRoom.size()).isEqualTo(2);
    }



    private List<Room> getStubRoomList() {

        return Arrays.asList(
                new Room("room0", "tester0", RoomType.STREAMING),
                new Room("room1", "tester1", RoomType.STREAMING)
        );
    }

}
