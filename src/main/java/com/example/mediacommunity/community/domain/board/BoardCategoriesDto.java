package com.example.mediacommunity.community.domain.board;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BoardCategoriesDto {
    private List<String> categories;
}
