package com.onebook.frontapi.adaptor.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebook.frontapi.dto.member.ErrorResponse;
import com.onebook.frontapi.dto.member.MemberRegisterRequestDto;
import com.onebook.frontapi.feign.member.MemberClient;
import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class MemberAdaptor {

    private final MemberClient memberClient;

    public boolean register(@Valid MemberRegisterRequestDto memberRegisterRequestDto) {

        try {
            memberClient.registerRequest(memberRegisterRequestDto);

        }catch(FeignException e) {
            String errorJson = e.contentUTF8();
            // JSON 파싱하여 ErrorResponse로 변환 (Jackson 예시)
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                ErrorResponse errorResponse = objectMapper.readValue(errorJson, ErrorResponse.class);
                log.info("Error Title: {}", errorResponse.getTitle());
                log.info("Error Status: {}", errorResponse.getStatus());
                return false;
            } catch (JsonProcessingException jsonException) {
                log.info("Failed to parse error response: {}", jsonException.getMessage());
                return false;
            }
        }
        return true;
    }
}
