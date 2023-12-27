package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNickNameHistory;
import com.example.fastcampusmysql.domain.member.dto.NickNameHistoryDto;
import com.example.fastcampusmysql.domain.member.repository.MemberNickNameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository memberRepository;
    private final MemberNickNameHistoryRepository historyRepository;
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

    public List<MemberDto> getMembers(List<Long> memberIds) {
        var members = memberRepository.findAllByIdIn(memberIds);
        return members.stream()
                .map(this::toDto)
                .toList();
    }

    public List<NickNameHistoryDto> getNickNameHistories(Long memberId) {
        var histories = historyRepository.findAllByMemberId(memberId);
        return histories.stream()
                .map(this::toDto)
                .toList();
    }

    //dto 변환 메서드 정의

    public MemberDto toDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getBirthdate()
        );
    }

    private NickNameHistoryDto toDto(MemberNickNameHistory history) {
        return new NickNameHistoryDto(
                history.getId(),
                history.getMemberId(),
                history.getNickname(),
                history.getCreatedAt()
        );
    }

}
