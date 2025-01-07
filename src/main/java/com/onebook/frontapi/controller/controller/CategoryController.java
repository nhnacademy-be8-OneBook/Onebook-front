package com.onebook.frontapi.controller.controller;

import com.onebook.frontapi.dto.category.CategoryDTO;
import com.onebook.frontapi.dto.category.CreateCategoryDTO;
import com.onebook.frontapi.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;



    @GetMapping("/create")
    public String createCategoryForm(){
        return "category/categoryRegister";
    }


    @PostMapping("/create")
    public String createCategory(@ModelAttribute CreateCategoryDTO dto){
        categoryService.createCategory(dto);
        return "redirect:/";
    }

}
