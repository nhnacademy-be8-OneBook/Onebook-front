package com.onebook.frontapi.controller.dormantaccount;

import com.onebook.frontapi.dto.dooraymessage.AuthRequestDto;
import com.onebook.frontapi.dto.dooraymessage.AuthResponseDto;
import com.onebook.frontapi.service.dooraymessage.DoorayMessageService;
import com.onebook.frontapi.service.member.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dormant-account")
public class DormantAccountController {

    private final DoorayMessageService doorayMessageService;
    private final MemberService memberService;

    // 휴면 계정 전환용 두레이 인증 폼
    @GetMapping
    public String dormantAccountAuthenticationForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // jwt가 있으면 홈으로.
        Cookie[] cookies = request.getCookies();
        for(Cookie c : cookies) {
            if(c.getName().equals("Authorization")) {
                response.sendRedirect("/");
            }
        }

        return "my/member/doorayAuth";
    }

    // 휴면 계정 전환용 두레이 인증
    @PostMapping
    public ResponseEntity<AuthResponseDto> dormantAccountAuthentication(
            @RequestBody AuthRequestDto code,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        AuthResponseDto result = doorayMessageService.validateAuthCode(request, code.authCode());

        if(result.success()) {
            response.sendRedirect("/login");
        }

        return ResponseEntity.ok().body(result);
    }

    // 휴면 해제
    @GetMapping("/reactivate")
    public String reactivateDormantAccount(
            @RequestParam String loginId) {
        if(memberService.modifyStatusToActive(loginId)) {
            return "redirect:/login";
        }

        return "redirect:/error/401";
    }
}
