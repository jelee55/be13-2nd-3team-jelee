package com.beyond3.yyGang.comment;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CommentResponseDto {

    private Long id;
    private String content;
    private Long userId;
    private Long boardId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getUserId();
        this.boardId = comment.getBoard().getId();
        this.createdAt = comment.getCreateAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
