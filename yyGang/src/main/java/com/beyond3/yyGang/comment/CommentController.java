package com.beyond3.yyGang.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board/{boardId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping
    public ResponseEntity<CommentResponseDto> save (
            @PathVariable(value = "boardId") Long boardId,
            @RequestBody CommentRequestDto requestDto){


        commentService.save(boardId, requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable Long id, @RequestBody CommentRequestDto requestDto){

        commentService.update(id, requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    // 특정 게시글 댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long boardId){

        return ResponseEntity.ok(commentService.getComments(boardId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        commentService.delete(id);
        return ResponseEntity.ok(id);
    }

}
