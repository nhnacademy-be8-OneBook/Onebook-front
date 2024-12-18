package com.onebook.frontapi.controller.myInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myInfo")
public class MyInfoController {

    @GetMapping
    public String myInfoForm() {
        return "myInfo";
    }
}
