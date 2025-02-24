package com.beyond3.yyGang.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardReporsitory extends JpaRepository<Board, Long> {
}
