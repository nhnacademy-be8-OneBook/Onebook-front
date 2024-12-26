package com.onebook.frontapi.controller.join;

import com.onebook.frontapi.dto.member.MemberRegisterRequestDto;
import com.onebook.frontapi.service.join.JoinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/join")
public class JoinController {

    private final JoinService joinService;

    @GetMapping
    public String registerForm() {
        return "auth/register";
    }

    @PostMapping
    public String memberRegister(@ModelAttribute @Valid MemberRegisterRequestDto memberRegisterRequestDto) {
        if(joinService.memberJoin(memberRegisterRequestDto)) {
            return "redirect:/login";
        }
        return "auth/register";
    }

}
