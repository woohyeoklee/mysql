package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Member {

    private final Long id;
    private String nickname;
    private final String email;
    private final LocalDate birthdate;
    private final LocalDate createdAt; // 로그를 위해 생성시간 추가

    //nickname.length() <= 10
    private static final Long MAX_NICKNAME_LENGTH = 10L;
    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthdate, LocalDate createdAt) {
        this.id = id;
        this.email =  Objects.requireNonNull(email);
        this.birthdate = birthdate;

        validateNickname(nickname);
        this.nickname = nickname;
        this.createdAt = createdAt == null ? LocalDate.now() : createdAt;
//
//        if (nickname.length() > MAX_NICKNAME_LENGTH) {
//            throw new IllegalArgumentException("nickname length must be less than " + MAX_NICKNAME_LENGTH);
//        }

    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.length() > MAX_NICKNAME_LENGTH) {
            throw new IllegalArgumentException("Invalid nickname");
        }
    }

    public void changeNickname(String other) {
        validateNickname(other);
        nickname = other;
    }
}
