package com.beyond3.yyGang.board;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 검사 기능 활성화
public class TimeStamped {

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
