package com.onebook.frontapi.service.category;

import com.onebook.frontapi.dto.category.CategoryDTO;
import com.onebook.frontapi.dto.category.CreateCategoryDTO;
import com.onebook.frontapi.feign.category.CategoryClient;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryClient categoryClient;

    public CategoryDTO createCategory(CreateCategoryDTO dto) {
        return categoryClient.createCategory(dto);
    }

    public List<CategoryDTO> getTopCategories() {
        List<CategoryDTO> topCategories =  categoryClient.getTopCategories();

        for (CategoryDTO category : topCategories) {
            List<CategoryDTO> subCategories = categoryClient.getSubCategories(category.getCategoryId());  // 하위 카테고리 조회 API 호출
            category.setSubCategories(subCategories);
        }

        return topCategories;
    }
}
