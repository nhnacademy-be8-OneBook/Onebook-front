package com.onebook.frontapi.controller.home;

import com.onebook.frontapi.feign.auth.AuthFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @Value("${server.port}")
    private String port;

    private final AuthFeignClient authFeignClient;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("serverPort", port);
        String s = authFeignClient.returnString();
        log.info("string : {}", s);

        return "home";
    }
}