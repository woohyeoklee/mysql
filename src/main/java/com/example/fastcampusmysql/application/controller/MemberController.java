package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.dto.NickNameHistoryDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    @PostMapping("/")
    public MemberDto registerMember(@RequestBody RegisterMemberCommand command) {
        var Member = memberWriteService.createMember(command);
        return memberReadService.toDto(Member);
    }
    @GetMapping("/{id}")
    public MemberDto getMember(Long id) {
        return memberReadService.getMember(id);
    }
    @PostMapping("/{id}/nickname")
    public MemberDto changeNickname(@PathVariable Long id, @RequestBody String nickname) {
        memberWriteService.changeNickname(id, nickname);
        return memberReadService.getMember(id);
    }
    @GetMapping("/{memberId}/nickname-histories")
    public List<NickNameHistoryDto> getNickNameHistories(@PathVariable Long memberId) {
        return memberReadService.getNickNameHistories(memberId);
    }
}
