package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNickNameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNickNameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;
    private final MemberNickNameHistoryRepository historyRepository;

    // member entity를 생성한다.
    // 파라미터를 RegisterMemberCommand로 받는다.
    //val member = Member of(registerMemberCommand)
    //memberRepository.save(member)
    public Member createMember(RegisterMemberCommand command) {

        var member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthdate(command.birthdate())
                .build();
        var savedMember = memberRepository.save(member);

        savedMemberNickNameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long id, String nickname) {
        var member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 없습니다."));
        member.changeNickname(nickname);
        var savedMember = memberRepository.save(member);

        savedMemberNickNameHistory(savedMember);
    }

    private void savedMemberNickNameHistory(Member member) {
        var history = MemberNickNameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        historyRepository.save(history);
    }

}
