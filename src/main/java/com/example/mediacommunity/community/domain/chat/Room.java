package com.example.mediacommunity.community.domain.chat;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
//@ToString
public class Room {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private String presenter;

    @Enumerated
    private RoomType roomType = RoomType.CHAT;

    public Room(String roomName, String presenter, RoomType roomType) {
        this.roomName = roomName;
        this.presenter = presenter;
        this.roomType = roomType;
    }
}
