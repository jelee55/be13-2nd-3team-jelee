package com.beyond3.yyGang.board.dto;

import com.beyond3.yyGang.board.entity.Board;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class BoardResponseDto{
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String userName;
    private String userEmail;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;



    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userId = board.getUser().getUserId();
        this.userEmail = board.getUser().getEmail();
        this.userName = board.getUser().getName();
        this.createdAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
    }



}
