package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;

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
        return memberRepository.save(member);
    }

}
