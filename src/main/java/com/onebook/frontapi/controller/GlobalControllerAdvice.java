package com.onebook.frontapi.controller;

import com.onebook.frontapi.dto.category.CategoryDTO;
import com.onebook.frontapi.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final CategoryService categoryService;

    @Autowired
    public GlobalControllerAdvice(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 모든 요청에서 topCategories를 자동으로 추가
    @ModelAttribute("topCategories")
    public List<CategoryDTO> getTopCategories() {
        List<CategoryDTO> categories = categoryService.getTopCategories();
        return categories;
    }
}