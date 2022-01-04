package com.example.mediacommunity.community.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Message {
    private UUID roomId;
    private String writer;
    private String message;
}
