package com.onebook.frontapi.controller.my.home;

import com.onebook.frontapi.dto.member.MemberResponse;
import com.onebook.frontapi.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/my")
public class MyHomeController {

    private final MemberService memberService;

    @GetMapping("/home")
    public String myHome(Model model) {
        MemberResponse memberResponse = memberService.getMember();
        model.addAttribute("member", memberResponse);
        return "my/home/home";
    }

}
