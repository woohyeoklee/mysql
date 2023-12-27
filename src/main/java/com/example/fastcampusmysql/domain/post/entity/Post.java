package com.example.fastcampusmysql.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Post {

    private final Long id;
    private final Long memberId;
    private final String contents;
    private final LocalDate createdDate;
    private final LocalDate createdAt;

    @Builder
    public Post(Long id, Long memberId, String title, String content, LocalDate createdDate, LocalDate createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(content);
        this.createdDate = createdDate == null ? LocalDate.now() : createdDate;
        this.createdAt = createdAt == null ? LocalDate.now() : createdAt;
    }
}
