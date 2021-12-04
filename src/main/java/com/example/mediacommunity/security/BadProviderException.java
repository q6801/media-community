package com.example.mediacommunity.security;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

public class BadProviderException extends InternalAuthenticationServiceException {
    public BadProviderException(String message) {
        super(message);
    }
}
