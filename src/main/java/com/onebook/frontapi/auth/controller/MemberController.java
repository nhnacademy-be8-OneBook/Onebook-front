package com.onebook.frontapi.auth.controller;

import com.onebook.frontapi.auth.dto.Member;
import com.onebook.frontapi.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public Member test() {
        return memberService.test();
    }
}
