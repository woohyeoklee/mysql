package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowReadService {
    private final FollowRepository followRepository;

    public List<Follow> getFollowing(Long memberId) {
        return followRepository.findAllByFromMemberId(memberId);
    }
    public List<Follow> getFollower(Long memberId) {
        return followRepository.findAllByToMemberId(memberId);
    }

}
