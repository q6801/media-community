package com.example.mediacommunity.community.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class MsgInfoDto {
    private UUID id;
    private String roomName;
}
