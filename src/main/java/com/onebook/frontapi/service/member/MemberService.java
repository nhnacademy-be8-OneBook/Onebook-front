package com.onebook.frontapi.service.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebook.frontapi.dto.grade.GradeFeignResponse;
import com.onebook.frontapi.dto.grade.GradeResponse;
import com.onebook.frontapi.dto.member.*;
import com.onebook.frontapi.feign.member.MemberClient;
import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberClient memberClient;

    /**
     * 회원 가입
     */
    public boolean joinMember(@Valid MemberRegisterRequest memberRegisterRequest) {
        try {

            MemberFeignResponse memberFeignResponse = memberClient.joinRequest(memberRegisterRequest);

        }catch(FeignException e) {
            String errorJson = e.contentUTF8();

            if (errorJson == null || errorJson.isEmpty()) {
                log.error("FeignException: 회원가입에 대한 task의 응답이 비어있습니다.");
                return false;
            }

            // JSON 파싱하여 ErrorResponse로 변환 (Jackson 예시)
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                ErrorResponse errorResponse = objectMapper.readValue(errorJson, ErrorResponse.class);
                log.error("Error Title: {}", errorResponse.getTitle());
                log.error("Error Status: {}", errorResponse.getStatus());
                return false;
            } catch (JsonProcessingException jsonException) {
                log.error("Failed to parse error response: {}", jsonException.getMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * 회원 조회
     */
    public MemberResponse getMember() {
        try {
            MemberFeignResponse memberFeignResponse = memberClient.getRequest();
            return MemberResponse.from(memberFeignResponse);
        }catch(FeignException e) {{
            throw new RuntimeException("task api 로부터 회원 조회 실패.");
        }}
    }

    /**
     * 회원정보 수정
     */
    public MemberResponse modifyMember(MemberModifyRequest memberModifyRequest) {
            MemberFeignResponse memberFeignResponse = memberClient.modifyRequest(memberModifyRequest);
            return MemberResponse.from(memberFeignResponse);
    }

    /**
     * 회원 등급 조회
     */
    public GradeResponse getMemberGrade() {
        try {
            GradeFeignResponse gradeFeignResponse = memberClient.getMemberGradeRequest();
            GradeResponse gradeResponse = new GradeResponse(
                    gradeFeignResponse.name(),
                    gradeFeignResponse.accumulationRate(),
                    gradeFeignResponse.description()
            );
            return gradeResponse;
        }catch(FeignException e) {
            throw new RuntimeException("task api 로부터 회원 등급 조회 실패");
        }
    }

    /**
     * 회원 여부 조회
     */
    public boolean checkMembership(MembershipCheckRequest membershipCheckRequest) {
        MembershipCheckFeignResponse membershipCheckFeignResponse = memberClient.checkMembershipRequest(membershipCheckRequest);
        if(Objects.isNull(membershipCheckFeignResponse)) {
            throw new IllegalArgumentException("MembershipCheckFeignResponse 가 null 입니다.");
        }

        if(membershipCheckFeignResponse.isMember()) {
            return true;
        }

        return false;
    }

    /**
     * 회원 탈퇴
     */
    public boolean deleteMember() {
        ResponseEntity<Void> result = memberClient.deleteRequest();
        if(result.getStatusCode().is2xxSuccessful()) {
            return true;
        }

        return false;
    }

    /**
     * 회원 상태 'ACTIVE'로 변경
     */
    public boolean modifyStatusToActive(String loginId) {
        ResponseEntity<Void> result = memberClient.modifyStatusToActive(loginId);
        if(result.getStatusCode().is2xxSuccessful()) {
            return true;
        }

        return false;
    }

}