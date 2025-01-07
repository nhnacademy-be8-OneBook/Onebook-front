package com.onebook.frontapi.controller.controller;

import com.onebook.frontapi.dto.category.CategoryDTO;
import com.onebook.frontapi.dto.category.CreateCategoryDTO;
import com.onebook.frontapi.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;



    @GetMapping("/create")
    public String createCategoryForm(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                     Model model) {
        if(Objects.nonNull(categoryId)) {
            model.addAttribute("categoryId", categoryId);
        }


        return "category/categoryRegister";
    }


    @PostMapping("/create")
    public String createCategory(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                 @RequestParam("categoryName") String name,
                                 Model model) {
        CreateCategoryDTO dto = new CreateCategoryDTO();
        dto.setCategoryName(name);
        if(Objects.isNull(categoryId)) {
            dto.setCategory(null);
        }else{
            dto.setCategory(categoryService.getCategoryById(categoryId));
        }
        categoryService.createCategory(dto);
        return "redirect:/";
    }

}
