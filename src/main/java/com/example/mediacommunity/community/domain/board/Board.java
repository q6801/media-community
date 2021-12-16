package com.example.mediacommunity.community.domain.board;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String writerId;
    private int viewCnt;
    private String title;

    public void updateBoardWithDto(BoardEditingDto updateParam) {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        this.content = updateParam.getContent();
        this.title = updateParam.getTitle();
    }

    public void increaseViewCnt() {
        this.viewCnt += 1;
    }

    public static class Builder {
        private String content;
        private String writerId;
        private int viewCnt;
        private String title;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private Long id;

        public Builder() {
        }

        public Builder content(String val) {
            Assert.hasText(val, "content must not be empty");
            content = val;
            return this;
        }
        public Builder writerId(String val) {
            Assert.hasText(val, "writerId must not be empty");
            writerId = val;
            return this;
        }
        public Builder title(String val) {
            Assert.hasText(val, "title must not be empty");
            title = val;
            return this;
        }
        public Builder id(Long val) {
            Assert.notNull(val, "id must not be empty");
            id = val;
            return this;
        }

        public Builder createdAt(Timestamp val) {
            Assert.notNull(val, "createdAt must not be empty");
            createdAt = val;
            return this;
        }

        public Builder updatedAt(Timestamp val) {
            Assert.notNull(val, "updatedAt must not be empty");
            updatedAt = val;
            return this;
        }

        public Builder viewCnt(int val) {
            Assert.notNull(val, "viewCnt must not be empty");
            viewCnt = val;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }

    private Board(Builder builder) {
        this.content = builder.content;
        this.writerId = builder.writerId;
        this.viewCnt = builder.viewCnt;
        this.title = builder.title;
        this.id = builder.id;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }
}
