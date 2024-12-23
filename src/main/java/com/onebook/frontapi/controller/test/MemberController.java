package com.onebook.frontapi.controller.test;

import com.onebook.frontapi.dto.member.Member;
import com.onebook.frontapi.feign.member.MemberClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/test")
//public class MemberController {
//
//    private final MemberClient memberClient;
//
//    @GetMapping
//    public Member test() {
//        return memberClient.test();
//    }
//}
