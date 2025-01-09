package com.onebook.frontapi.controller.admin;

import com.onebook.frontapi.dto.member.MemberViewDto;
import com.onebook.frontapi.service.member.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Member;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(Model model) {
        MemberViewDto memberViewDto = memberService.getMember();
        model.addAttribute("member", memberViewDto);

        return "admin/home/home";
    }
}
