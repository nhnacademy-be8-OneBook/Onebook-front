package com.onebook.frontapi.controller.my.member;

import com.onebook.frontapi.dto.member.MemberModifyRequestDto;
import com.onebook.frontapi.dto.member.MemberViewDto;
import com.onebook.frontapi.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/my")
public class MemberController {

    private final MemberService memberService;

    // 개인정보 수정
    @GetMapping("/member/modify")
    public String memberInfoModifyForm(Model model) {
        MemberViewDto member = memberService.getMember();
        return "my/member/modify";
    }

    @PostMapping("/member/modify")
    public String memberInfoModify(@RequestBody @Valid MemberModifyRequestDto memberModifyRequestDto) {
        return null;

    }
}
