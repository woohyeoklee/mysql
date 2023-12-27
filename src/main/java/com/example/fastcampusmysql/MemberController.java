package com.example.fastcampusmysql;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    @PostMapping("/members")
    public MemberDto registerMember(@RequestBody RegisterMemberCommand command) {
        var Member = memberWriteService.createMember(command);
        return memberReadService.toDto(Member);
    }

    @GetMapping("/members/{id}")
    public MemberDto getMember(Long id) {
        return memberReadService.getMember(id);
    }

    @PostMapping("/members/{id}/nickname")
    public MemberDto changeNickname(@PathVariable Long id, @RequestBody String nickname) {
        memberWriteService.changeNickname(id, nickname);
        return memberReadService.getMember(id);
    }
}
