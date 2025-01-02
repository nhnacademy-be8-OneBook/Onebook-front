package com.onebook.frontapi.feign.member;

import com.onebook.frontapi.dto.member.MemberRegisterRequestDto;
import com.onebook.frontapi.dto.member.MemberResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "memberClient", url="localhost:8210/task/members")
@FeignClient(name = "memberClient", url = "${onebook.gatewayUrl}")
public interface MemberClient {
    @PostMapping("/task/members")
    MemberResponseDto registerRequest(@RequestBody MemberRegisterRequestDto memberRegisterRequestDto);
}

