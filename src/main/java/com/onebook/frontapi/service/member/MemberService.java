package com.onebook.frontapi.service.member;

import com.onebook.frontapi.adaptor.member.MemberAdaptor;
import com.onebook.frontapi.dto.grade.GradeFeignResponse;
import com.onebook.frontapi.dto.grade.GradeResponse;
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
    public boolean joinMember(MemberRegisterRequest memberRegisterRequest) {
        if(memberAdaptor.join(memberRegisterRequest)) {
            return true;
        }
        return false;
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

    /**
     * 회원 순수 금액 조회 (+등급 업데이트)
     */
    public Integer getMemberNetPaymentAmount(Long memberId) {
        ResponseEntity<Integer> result = memberClient.getMemberNetPaymentAmount(memberId);
        if(Objects.isNull(result)) {
            return 0;
        }
        return result.getBody();
    }

}