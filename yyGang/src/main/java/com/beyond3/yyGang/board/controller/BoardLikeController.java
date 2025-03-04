//package com.beyond3.yyGang.board.controller;
//
//import com.beyond3.yyGang.board.entity.BoardLike;
//import com.beyond3.yyGang.board.service.BoardLikeService;
//import com.beyond3.yyGang.board.service.BoardService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/like")
//@RequiredArgsConstructor
//public class BoardLikeController {
//
//    private final BoardLikeService boardLikeService;
//    private final BoardService boardService;
//
//    @PostMapping("/{boardId}")
//    public ResponseEntity<String> like(@PathVariable Long boardId, @RequestParam Long userId){
//        boolean isLiked = boardLikeService.addLike(boardId, userId);
//        if (isLiked){
//            return ResponseEntity.ok("게시글를 좋아요 함");
//        }
//        return ResponseEntity.badRequest().body("이미 좋아요가 눌러졌거나, 게시글이 존재하지 않음");
//    }
//}
