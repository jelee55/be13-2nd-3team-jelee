package com.beyond3.yyGang.board.repository;

import com.beyond3.yyGang.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
