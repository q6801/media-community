package com.example.mediacommunity.community.domain.heart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HeartDto {
    private int heartCnt;
    private Boolean pushed;
}