package com.example.mediacommunity.community.domain.board;

import com.example.mediacommunity.community.domain.BaseTimeEntity;
import com.example.mediacommunity.community.domain.category.BoardCategory;
import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.reply.Reply;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"member", "replies", "hearts", "boardCategory"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"member", "replies", "hearts", "boardCategory"})
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @Setter(AccessLevel.NONE)
    private int viewCnt;

    @Setter(AccessLevel.NONE)
    private int replyCnt;

    @Setter(AccessLevel.NONE)
    private int heartCnt;

    private String title;

    private Boolean anonymous=false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writerId")
    private Member member;

    @OneToMany(mappedBy="board", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Heart> hearts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardCategory boardCategory;

    @Builder
    private Board(String content, String title, Timestamp updatedAt, Boolean anonymous) {
        this.content = content;
        this.title = title;
        this.updatedAt = updatedAt;
        this.anonymous = anonymous;
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getBoards().remove(this);
        }
        this.member = member;
        member.getBoards().add(this);   // 주인이 아니라서 저장 시 사용 안됨
    }

    public void setBoardCategory(BoardCategory bc) {
        this.boardCategory = bc;
    }

    public static Board convertBoardRequestDtoToBoard(BoardRequestDto boardDto, Member member, BoardCategory category) {
        Board board = new Board();
        BeanUtils.copyProperties(boardDto, board);
        board.setMember(member);
        board.boardCategory = category;
        return board;
    }

    public void updateBoardWithDto(BoardRequestDto updateParams, BoardCategory category) {
        BeanUtils.copyProperties(updateParams, this);
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        this.boardCategory = category;
    }

    public BoardDto convertBoardToBoardDto() {
        String writer = checkAnomymousStatus();
        BoardDto boardDto = new BoardDto();
        BeanUtils.copyProperties(this, boardDto);
        boardDto.setWriter(writer);
        return boardDto;
    }

    private String checkAnomymousStatus() {
        String writer;
        if (this.anonymous==true) {
            writer = "익명의 누군가";
        } else {
            writer=this.member.getNickname();
        }
        return writer;
    }

    public void increaseViewCnt() { this.viewCnt += 1; }
    public void increaseHeartCnt() {
        this.heartCnt += 1;
    }
    public void decreaseHeartCnt() {
        if(this.heartCnt > 0) {
            this.heartCnt -= 1;
        }
    }
    public void decreaseReplyCnt() {
        if (this.replyCnt > 0) {
            this.replyCnt -= 1;
        }
    }
    public void increaseReplyCnt() {
        this.replyCnt += 1;
    }
}
