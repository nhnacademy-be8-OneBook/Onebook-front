package com.onebook.frontapi.feign.member;

import com.onebook.frontapi.dto.member.MemberRegisterRequestDto;
import com.onebook.frontapi.dto.member.MemberResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "memberClient", url="localhost:8210/task/members")
@FeignClient(name = "memberClient", url = "${onebook.gatewayUrl}")
public interface MemberClient {

    /**
     * 회원 가입
     * @param memberRegisterRequestDto
     * @return memberResponseDto
     */
    @PostMapping("/task/members")
    MemberResponseDto joinRequest(@RequestBody MemberRegisterRequestDto memberRegisterRequestDto);

    /**
     * 멤버 조회
     * @return memberResponseDto
     */
    @GetMapping("/task/members")
    MemberResponseDto getRequest();


}

