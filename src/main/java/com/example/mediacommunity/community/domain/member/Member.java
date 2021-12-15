package com.example.mediacommunity.community.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Data
@Entity
@Table(name="Members")
public class Member {
    @Id
    private String loginId;
    private String password;
    private String nickname;
    private String provider;
    private String imageUrl;

    public Member() {};
}
