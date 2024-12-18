package com.onebook.frontapi.controller.join;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/join")
public class JoinController {

    @GetMapping
    public String joinForm() {
        return "join/joinForm";
    }

    @PostMapping
    public String join() {
        return "redirect:/";
    }

}
