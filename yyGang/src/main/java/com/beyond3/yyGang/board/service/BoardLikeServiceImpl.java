//package com.beyond3.yyGang.board.service;
//
//import com.beyond3.yyGang.board.entity.BoardLike;
//import com.beyond3.yyGang.board.entity.BoardLikeId;
//import com.beyond3.yyGang.board.repository.BoardRepository;
//import com.beyond3.yyGang.board.repository.LikeRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class BoardLikeServiceImpl implements BoardLikeService {
//
//    private final LikeRepository likeRepository;
//    private final BoardRepository boardRepository;
//
//    @Override
//    public boolean addLike(Long boardId, Long userId) {
//        if (!boardRepository.existsById(boardId)){
//            return false;
//        }
//
//        BoardLikeId likeId = new BoardLikeId(boardId, userId);
//        if (likeRepository.existsById(likeId)){
//            return false;
//        }
//
//
//
//
//        return false;
//    }
//}
