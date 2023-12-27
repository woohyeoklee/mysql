package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFollowingUsecase {
    private final FollowReadService followReadService;
    private final MemberReadService memberReadService;

    public List<MemberDto> excute(Long memberId) {
        // fromId로 following을 조회, MemberDto로 변환
        // followReadService.getFollowing(memberId);
        var followings = followReadService.getFollowing(memberId);
        var followingMemberIds = followings.stream()
                .map(Follow::getToMemberId)
                .toList();
        return memberReadService.getMembers(followingMemberIds);
    }
}
