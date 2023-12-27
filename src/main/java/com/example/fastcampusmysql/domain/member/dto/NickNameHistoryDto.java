package com.example.fastcampusmysql.domain.member.dto;

import java.time.LocalDate;

public record NickNameHistoryDto (
        Long id,
        Long memberId,
        String nickname,
        LocalDate createdAt
){
}
