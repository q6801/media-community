package com.example.mediacommunity.community.domain.chat;

import com.example.mediacommunity.community.service.Pagination;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MsgRoomsDto {
    private List<Room> rooms;
    private Pagination pagination;
}
