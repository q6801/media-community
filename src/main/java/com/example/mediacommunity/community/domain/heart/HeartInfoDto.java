package com.example.mediacommunity.community.domain.heart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeartInfoDto {
    private Long heartsCnt;
    private Boolean pushed;
}
