package com.onebook.frontapi.service.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebook.frontapi.adaptor.member.MemberAdaptor;
import com.onebook.frontapi.dto.member.ErrorResponse;
import com.onebook.frontapi.dto.member.MemberRegisterRequestDto;
import com.onebook.frontapi.dto.member.MemberResponseDto;
import com.onebook.frontapi.dto.member.MemberViewDto;
import com.onebook.frontapi.feign.member.MemberClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        MemberResponseDto memberResponseDto = memberClient.getRequest();
        return MemberViewDto.from(memberResponseDto);
    }




}