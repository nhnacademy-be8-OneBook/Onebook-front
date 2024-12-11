package com.onebook.frontapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("serverPort", port);

        return "home";
    }
}