package com.onebook.frontapi.auth.controller;

import com.onebook.frontapi.auth.dto.Member;
import com.onebook.frontapi.auth.feign.MemberClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class MemberController {

    private final MemberClient memberClient;

    @GetMapping
    public Member test() {
        return memberClient.test();
    }
}
