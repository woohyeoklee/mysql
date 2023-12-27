package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowMemberUsecase {
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;

    public void excute(Long fromId, Long toId) {

        var fromMember = memberReadService.getMember(fromId);
        var toMember = memberReadService.getMember(toId);

        followWriteService.createFollow(fromMember, toMember);
    }
}
