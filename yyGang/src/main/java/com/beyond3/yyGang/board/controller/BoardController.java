package com.beyond3.yyGang.board.controller;

import com.beyond3.yyGang.board.entity.Board;
import com.beyond3.yyGang.board.dto.BoardRequestDto;
import com.beyond3.yyGang.board.dto.BoardResponseDto;
import com.beyond3.yyGang.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BoardResponseDto> save(@RequestBody BoardRequestDto requestDto) {
        boardService.save(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "조회", description = "게시글 조회")
    public ResponseEntity<List<BoardResponseDto>> findAll(){
        List<BoardResponseDto> board = boardService.findAll();
        return ResponseEntity.ok(board);

    }
    @GetMapping("/{id}")
    @Operation(summary = "상세조회", description = "게시글 상세조회")
    public ResponseEntity<BoardResponseDto> findById(@PathVariable Long id){
        Board board = boardService.findById(id);
        System.out.println(board+"boarddddddd");

        return ResponseEntity.ok(new BoardResponseDto(board));

    }

    @PutMapping("/{id}")
    @Operation(summary = "수정", description = "게시글 수정")
    public ResponseEntity<BoardResponseDto> update(
            @PathVariable Long id,
            @RequestBody BoardRequestDto requestDto){

        BoardResponseDto board = boardService.update(id, requestDto);
        return ResponseEntity.ok(board);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id){
        boardService.delete(id);
        return ResponseEntity.ok(id);
    }

}
