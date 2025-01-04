package com.onebook.frontapi.controller.join;

import com.onebook.frontapi.dto.member.MemberRegisterRequestDto;
import com.onebook.frontapi.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/join")
public class JoinController {

    private final MemberService memberService;

    @GetMapping
    public String joinForm() {
        return "join/joinForm";
    }

    @PostMapping
    public String memberJoin(@ModelAttribute @Valid MemberRegisterRequestDto memberRegisterRequestDto) {
        if(memberService.joinMember(memberRegisterRequestDto)) {
            return "redirect:/login";
        }
        return "join/joinForm";
    }

}
