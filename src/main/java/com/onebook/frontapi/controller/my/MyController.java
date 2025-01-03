package com.onebook.frontapi.controller.my;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my")
public class MyController {

    @GetMapping("/home")
    public String myInfoForm() {
        return "my/home";
    }
}
