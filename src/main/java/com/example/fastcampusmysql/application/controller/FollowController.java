package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.FollowMemberUsecase;
import com.example.fastcampusmysql.application.usecase.GetFollowingUsecase;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follows")
public class FollowController {
    private final FollowMemberUsecase followMemberUsecase;
    private final GetFollowingUsecase getFollowingUsecase;

    @PostMapping("/{fromId}/{toId}")
    public void createFollow(@PathVariable Long fromId, @PathVariable Long toId) {
        followMemberUsecase.excute(fromId, toId);
    }

    @GetMapping("/members/{fromId}")
    public List<MemberDto> getFollowing(@PathVariable Long fromId) {
        return getFollowingUsecase.excute(fromId);
    }
}
