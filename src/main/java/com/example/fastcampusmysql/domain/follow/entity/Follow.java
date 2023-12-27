package com.example.fastcampusmysql.domain.follow.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Follow {

    private final Long id;
    private final Long fromMemberId;
    private final Long toMemberId;

    private final LocalDate createdAt;

    @Builder
    public Follow(Long id, Long fromMemberId, Long toMemberId, LocalDate createdAt) {
        this.id = id;
        this.fromMemberId = Objects.requireNonNull(fromMemberId);
        this.toMemberId = Objects.requireNonNull(toMemberId);
        this.createdAt = createdAt == null ? LocalDate.now() : createdAt; // 로그를 위해 생성시간 추가
    }
}
