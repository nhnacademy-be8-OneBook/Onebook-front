package com.onebook.frontapi.feign.member;

import com.onebook.frontapi.dto.grade.GradeFeignResponse;
import com.onebook.frontapi.dto.member.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@FeignClient(name = "memberClient", url="localhost:8210/task/members")
@FeignClient(name = "memberClient", url="${onebook.gatewayUrl}")
public interface MemberClient {

    /**
     * 회원 가입
     * @param memberRegisterRequest
     * @return MemberFeignResponse
     */
    @PostMapping("/task/members")
    MemberFeignResponse joinRequest(@RequestBody MemberRegisterRequest memberRegisterRequest);

    /**
     * 멤버 조회
     * @return MemberFeignResponse
     */
    @GetMapping("/task/members")
    MemberFeignResponse getRequest();

    /**
     * 멤버 로그인 기록 업데이트 by loginId
     */
    @PutMapping("/task/auth/{loginId}/login-history")
    void updateLoginHistoryRequest(@PathVariable("loginId") String loginId);

    /**
     * 멤버 수정
     * @param memberModifyRequest
     * @return MemberFeignResponse
     */
    @PutMapping("/task/members")
    MemberFeignResponse modifyRequest(@RequestBody MemberModifyRequest memberModifyRequest);

    // 회원 등급 조회.
    @GetMapping("/task/members/grade")
    GradeFeignResponse getMemberGradeRequest();

    // 회원 여부 조회
    @PostMapping("/task/members/membership")
    MembershipCheckFeignResponse checkMembershipRequest(MembershipCheckRequest membershipCheckRequest);

    // 회원 탈퇴
    @DeleteMapping("/task/members")
    ResponseEntity<Void> deleteRequest();

    // 회원 상태 'ACTIVE'로 변경
    @GetMapping("/task/members/status/{loginId}")
    ResponseEntity<Void> modifyStatusToActive(@PathVariable("loginId") String loginId);

}

