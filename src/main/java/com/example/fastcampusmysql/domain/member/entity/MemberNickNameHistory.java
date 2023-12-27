package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class MemberNickNameHistory {
    private final Long id;
    private final Long memberId;
    private final String nickname;

    private final LocalDate createdAt;

    @Builder
    public MemberNickNameHistory(Long id, Long memberId, String nickname, LocalDate createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = createdAt == null ? LocalDate.now() : createdAt; // 로그를 위해 생성시간 추가
    }

}
