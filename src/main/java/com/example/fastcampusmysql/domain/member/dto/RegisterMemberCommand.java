package com.example.fastcampusmysql.domain.member.dto;

import java.time.LocalDate;

public record RegisterMemberCommand(
        String nickname,
        String email,
        LocalDate birthdate ) {
}
