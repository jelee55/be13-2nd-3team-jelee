package com.beyond3.yyGang.board.controller;

import com.beyond3.yyGang.board.entity.Board;
import com.beyond3.yyGang.board.dto.BoardRequestDto;
import com.beyond3.yyGang.board.dto.BoardResponseDto;
import com.beyond3.yyGang.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Board", description = "자유 게시판")
@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    @Operation(summary = "등록", description = "게시글 등록")
    public ResponseEntity<BoardResponseDto> save(Principal principal, @RequestBody BoardRequestDto requestDto) {
        boardService.save(requestDto, principal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }




    @GetMapping
    @Operation(summary = "조회", description = "게시글 조회")
    public ResponseEntity<List<BoardResponseDto>> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size ){
        Page<BoardResponseDto> boardList = boardService.findAll(page, size);
        return ResponseEntity.ok(boardList.getContent());

    }




    @GetMapping("/{id}")
    @Operation(summary = "상세조회", description = "게시글 상세조회")
    public ResponseEntity<BoardResponseDto> findById(@PathVariable Long id){

        Board board = boardService.findById(id);

        return ResponseEntity.ok(new BoardResponseDto(board));

    }

    @PutMapping("/{id}")
    @Operation(summary = "수정", description = "게시글 수정")
    public ResponseEntity<BoardResponseDto> update(
            Principal principal,
            @PathVariable Long id,
            @RequestBody BoardRequestDto requestDto){

        BoardResponseDto board = boardService.update(principal, id, requestDto);
        return ResponseEntity.ok(board);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "삭제", description = "게시글 삭제")
    public ResponseEntity<Long> delete(Principal principal,@PathVariable Long id){
        boardService.delete(principal, id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}/likes")
    @Operation(summary = "좋아요 수", description = "게시글 좋아요 수")
    public ResponseEntity<Long> getBoardLikeCount(@PathVariable("id") Long boardId){
        long likeCount = boardService.getBoardLikeCount(boardId);
        return ResponseEntity.ok(likeCount);
    }

}
