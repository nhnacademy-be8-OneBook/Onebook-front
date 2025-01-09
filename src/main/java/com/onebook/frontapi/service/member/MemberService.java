package com.onebook.frontapi.service.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebook.frontapi.adaptor.member.MemberAdaptor;
import com.onebook.frontapi.dto.grade.GradeResponseDto;
import com.onebook.frontapi.dto.grade.GradeViewDto;
import com.onebook.frontapi.dto.member.*;
import com.onebook.frontapi.feign.member.MemberClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    // 회원가입 까지만 Adaptor 패턴 사용. 그 이후로 사용 X
    private final MemberAdaptor memberAdaptor;
    private final MemberClient memberClient;

    /**
     * 회원 가입
     */
    public boolean joinMember(MemberRegisterRequestDto memberRegisterRequestDto) {
        if(memberAdaptor.join(memberRegisterRequestDto)) {
            return true;
        }
        return false;
    }

    /**
     * 회원 조회
     */
    public MemberViewDto getMember() {
        try {
            MemberResponseDto memberResponseDto = memberClient.getRequest();
            return MemberViewDto.from(memberResponseDto);
        }catch(FeignException e) {{
            throw new RuntimeException("task api 로부터 member 조회 실패.");
        }}
    }

    /**
     * 회원정보 수정
     */
    public MemberViewDto modifyMember(MemberModifyRequestDto memberModifyRequestDto) {
            MemberResponseDto memberResponseDto = memberClient.modifyRequest(memberModifyRequestDto);
            return MemberViewDto.from(memberResponseDto);
    }

    /**
     * 회원 등급 조회
     */
    public GradeViewDto getMemberGrade() {
        GradeResponseDto gradeResponseDto = memberClient.getMemberGradeRequest();
        GradeViewDto gradeViewDto = new GradeViewDto(
                gradeResponseDto.name(),
                gradeResponseDto.accumulationRate(),
                gradeResponseDto.description()
                );
        return gradeViewDto;
    }

    /**
     * 회원 여부 조회
     */
    public boolean checkMembership(MembershipCheckRequestDto membershipCheckRequestDto) {
        MembershipCheckResponseDto membershipCheckResponseDto = memberClient.checkMembershipRequest(membershipCheckRequestDto);
        if(Objects.isNull(membershipCheckResponseDto)) {
            throw new IllegalArgumentException("MembershipCheckResponseDto 가 null 입니다.");
        }

        if(membershipCheckResponseDto.isMember()) {
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

}