package com.example.mediacommunity.community.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Getter
@MappedSuperclass
public abstract class BaseTimeEntity {
    @CreationTimestamp
    protected Timestamp createdAt;
    @UpdateTimestamp
    protected Timestamp updatedAt;
}
