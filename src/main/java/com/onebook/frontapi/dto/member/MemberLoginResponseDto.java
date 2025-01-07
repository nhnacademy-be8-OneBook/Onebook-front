package com.onebook.frontapi.dto.member;

public record MemberLoginResponseDto(
        String loginId,
        String password,
        String role
) {

}
