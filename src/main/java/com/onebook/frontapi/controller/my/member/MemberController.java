package com.onebook.frontapi.controller.my.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my")
public class MemberController {
    @GetMapping("/member/modify")
    public String memberInfoModify(Model model) {
        return "my/member/modify";
    }
}
