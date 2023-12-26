package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository memberRepository;
//
//    public Member getMember(Long id) {
//        return memberRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다."));
//    }
    public MemberDto getMember(Long id) {
        var Member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다."));
        return toDto(Member);
    }
    public MemberDto toDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getBirthdate()
        );
    }

}
