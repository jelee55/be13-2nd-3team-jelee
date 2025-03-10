package com.beyond3.yyGang.comment;

import java.security.Principal;
import java.util.List;

public interface CommentService {

    void save(Principal principal, Long boardId, CommentRequestDto requestDto);

    void update(Principal principal, Long id, CommentRequestDto requestDto);


    List<CommentResponseDto> getComments(Long boardId);

    void delete(Principal principal,Long id);
}
