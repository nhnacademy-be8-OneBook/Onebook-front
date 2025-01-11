package com.onebook.frontapi.controller.category;

import com.onebook.frontapi.dto.category.CategoryDTO;
import com.onebook.frontapi.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCategoryController {
    private final CategoryService categoryService;

    @GetMapping("/category-list")
    public String categoryList() {
        return "admin/categoryList";
    }

    @GetMapping("/category-mini-list")
    public String categoryMiniList(@RequestParam(defaultValue = "0") int page,
                                   Model model) {
        Page<CategoryDTO> categoryList = categoryService.getAllCategories(PageRequest.of(page, 10));
        model.addAttribute("categoryList", categoryList);

        return "admin/categoryMiniList";
    }



}
