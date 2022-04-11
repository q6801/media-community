package com.example.mediacommunity.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String id() default "tester0";

    String name() default "tester0";

    String role() default "ROLE_USER";
}
