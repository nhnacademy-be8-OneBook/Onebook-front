package com.onebook.frontapi.auth.service;

import com.onebook.frontapi.auth.dto.Member;
import com.onebook.frontapi.auth.feign.MemberClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberClient memberClient;

    public Member test() {
       return memberClient.test();
    }
}
