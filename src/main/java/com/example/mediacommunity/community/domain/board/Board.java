package com.example.mediacommunity.community.domain.board;

import lombok.*;
import org.springframework.util.Assert;

import java.sql.Timestamp;

@Getter
//@ToString
@EqualsAndHashCode
public class Board {
    private Long id;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String writerId;
    private int viewCnt;
    private String title;

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

        public Builder(Board board) {
            this.content = board.getContent();
            this.writerId = board.getWriterId();
            this.title = board.getTitle();
            this.viewCnt = board.getViewCnt();
            this.id = board.getId();
            this.createdAt = board.getCreatedAt();
            this.updatedAt = board.getUpdatedAt();
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
