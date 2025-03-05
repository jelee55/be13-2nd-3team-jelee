package com.beyond3.yyGang.comment;

import java.util.List;

public interface CommentService {

    void save(Long boardId, CommentRequestDto requestDto);

    void update(Long id, CommentRequestDto requestDto);


    List<CommentResponseDto> getComments(Long boardId);

    void delete(Long id);
}
