package com.example.fastcampusmysql.domain.post.dto;

import lombok.Builder;

@Builder
public record PostCommand(Long memberId, String contents) {
}
