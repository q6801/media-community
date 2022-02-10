package com.example.mediacommunity.community.service;

import com.example.mediacommunity.community.domain.chat.StreamingRoom;
import com.example.mediacommunity.community.repository.streaming.StreamingRoomRepository;
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
public class StreamingRoomServiceTest {
    @Mock
    private StreamingRoomRepository streamingRoomRepository;

    @InjectMocks
    private StreamingRoomService streamingRoomService;


    private UUID id0 = UUID.randomUUID();
    private UUID id1 = UUID.randomUUID();

    @Test
    public void createRoom() {
        // given
        StreamingRoom room = getStubRoomList().get(1);

        //when
        StreamingRoom createdRoom = streamingRoomService
                .createRoom(room.getRoomName(), room.getPresenter());

        //then
        assertThat(room).isEqualTo(createdRoom);
    }

    @Test
    public void findById() {
        //given
        StreamingRoom room = getStubRoomList().get(0);
        given(streamingRoomRepository.findByRoomId(room.getId())).willReturn(room);

        //when
        StreamingRoom foundRoom = streamingRoomService.findById(room.getId());

        //then
        assertThat(foundRoom).isEqualTo(room);
    }

    @Test
    public void findByPresenter() {
        //given
        StreamingRoom room = getStubRoomList().get(0);
        given(streamingRoomRepository.findByPresenter(room.getPresenter()))
                .willReturn(Optional.of(room));

        //when
        Optional<StreamingRoom> foundRoom = streamingRoomService.findByPresenter(room.getPresenter());

        //then
        assertThat(foundRoom.get()).isEqualTo(room);
    }

    @Test
    public void findAllRoom() {
        //given
        given(streamingRoomRepository.findAllRoom()).willReturn(getStubRoomList());

        //when
        List<StreamingRoom> allRoom = streamingRoomService.findAllRoom();

        //then
        System.out.println(getStubRoomList().get(0));
        System.out.println(allRoom);
        assertThat(allRoom).contains(getStubRoomList().get(0));
        assertThat(allRoom.size()).isEqualTo(2);
    }



    private List<StreamingRoom> getStubRoomList() {

        return Arrays.asList(
                StreamingRoom.builder()
                        .roomName("room0")
                        .id(id0)
                        .presenter("tester0").build(),
                StreamingRoom.builder()
                        .roomName("room1")
                        .presenter("tester1").build()
        );
    }

}
