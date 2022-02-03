package com.example.mediacommunity.security.userInfo;

import lombok.Getter;

import java.security.Principal;

@Getter
public class StompPrincipal implements Principal {
    private String memberId;
    private String userUUID;
    private Boolean hasName = false;

    public StompPrincipal(String userUUID) {
        this.userUUID = userUUID;
    }
    public StompPrincipal(String userUUID, String memberId) {
        this.userUUID = userUUID;
        this.memberId = memberId;
        hasName = true;
    }
    public String getMemberId() {
        return memberId;
    }
    @Override
    public String getName() {
        return userUUID;
    }
}
