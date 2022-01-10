package com.example.mediacommunity.community.domain.board;

import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.reply.Reply;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int viewCnt;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writerId")
    private Member member;

    @OneToMany(mappedBy="board", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Heart> hearts = new ArrayList<>();

    @Builder
    private Board(String content, int viewCnt, String title, Timestamp createdAt, Timestamp updatedAt) {
        this.content = content;
        this.viewCnt = viewCnt;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getBoards().remove(this);
        }
        this.member = member;
        member.getBoards().add(this);   // 주인이 아니라서 저장 시 사용 안됨
    }

    public static Board convertBoardAddingDtoToBoard(BoardAddingDto boardDto, Member member) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        Board board = Board.builder()
                .content(boardDto.getContent())
                .title(boardDto.getTitle())
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .viewCnt(0).build();
        board.setMember(member);
        return board;
    }

    public void updateBoardWithDto(BoardAddingDto updateParam) {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        this.content = updateParam.getContent();
        this.title = updateParam.getTitle();
    }

    public BoardInfoDto convertBoardToBoardInfoDto() {
        return new BoardInfoDto(this.id, this.content, this.createdAt,
                this.updatedAt, this.viewCnt, this.title, this.member.getNickname());
    }

    public void increaseViewCnt() {
        this.viewCnt += 1;
    }
}
