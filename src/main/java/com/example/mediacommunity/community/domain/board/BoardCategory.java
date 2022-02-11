package com.example.mediacommunity.community.domain.board;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class BoardCategory {
    @Id
    private String id;
}
