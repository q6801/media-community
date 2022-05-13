package com.example.mediacommunity.community.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Getter
@MappedSuperclass
@ToString
@EqualsAndHashCode
@lombok.Generated
public abstract class BaseTimeEntity {
    @CreationTimestamp
    protected Timestamp createdAt;
    @UpdateTimestamp
    protected Timestamp updatedAt;
}
