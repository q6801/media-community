package com.example.mediacommunity.community.domain.heart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HeartDto {
    private int heartCnt;
    private Boolean pushed;
}
