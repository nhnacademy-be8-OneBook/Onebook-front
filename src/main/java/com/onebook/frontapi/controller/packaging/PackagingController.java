package com.onebook.frontapi.controller.packaging;

import com.onebook.frontapi.adaptor.packaging.PackagingResponseAdaptor;
import com.onebook.frontapi.service.packaging.PackagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PackagingController {
    private final PackagingService packagingService;

    @GetMapping("/task/packagings")
    public String packagings(Model model) {
        List<PackagingResponseAdaptor> packagingList = packagingService.getAllPackaging();

        model.addAttribute("packagingList", packagingList);
        return "packaging/packagings";
    }
}