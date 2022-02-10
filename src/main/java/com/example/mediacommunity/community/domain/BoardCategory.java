package com.example.mediacommunity.community.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class BoardCategory {
    @Id
    private String id;
}
