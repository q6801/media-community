package com.example.mediacommunity.community.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    public enum Messagetype {
        ENTER, COMM
    };

    private Messagetype messageType;
    private String roomId;
    private String sender;
    private String message;
}
