package com.onebook.frontapi.feign.member;

import com.onebook.frontapi.dto.grade.GradeResponseDto;
import com.onebook.frontapi.dto.member.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@FeignClient(name = "memberClient", url="localhost:8210/task/members")
@FeignClient(name = "memberClient", url="${onebook.gatewayUrl}")
public interface MemberClient {

    /**
     * 회원 가입
     * @param memberRegisterRequestDto
     * @return MemberResponseDto
     */
    @PostMapping("/task/members")
    MemberResponseDto joinRequest(@RequestBody MemberRegisterRequestDto memberRegisterRequestDto);

    /**
     * 멤버 조회
     * @return MemberResponseDto
     */
    @GetMapping("/task/members")
    MemberResponseDto getRequest();

    /**
     * 멤버 로그인 기록 업데이트 by loginId
     */
    @PutMapping("/task/auth/{loginId}/login-history")
    void updateLoginHistoryRequest(@PathVariable("loginId") String loginId);

    /**
     * 멤버 수정
     * @param memberModifyRequestDto
     * @return MemberResponseDto
     */
    @PutMapping("/task/members")
    MemberResponseDto modifyRequest(@RequestBody MemberModifyRequestDto memberModifyRequestDto);

    @GetMapping("/task/members/grade")
    GradeResponseDto getMemberGradeRequest();

    // 회원 여부 조회
    @PostMapping("/task/members/membership")
    MembershipCheckResponseDto checkMembershipRequest(MembershipCheckRequestDto membershipCheckRequestDto);

    // 회원 탈퇴
    @DeleteMapping("/task/members")
    ResponseEntity<Void> deleteRequest();

}

