package com.example.fastcampusmysql.domain.post.dto;

import java.time.LocalDate;

public record DailyPost(
        Long memberId,
        LocalDate date,
        Long postCount) {
}
