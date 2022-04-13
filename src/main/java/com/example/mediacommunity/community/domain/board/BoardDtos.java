package com.example.mediacommunity.community.domain.board;

import com.example.mediacommunity.community.service.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardDtos {
    private List<BoardDto> boardInfoDtos;
    private Pagination pagination;
}
