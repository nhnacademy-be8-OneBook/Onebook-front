package com.onebook.frontapi.feign.category;


import com.onebook.frontapi.dto.category.CategoryDTO;
import com.onebook.frontapi.dto.category.CreateCategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "categoryClient",  url = "http://localhost:8510")
public interface CategoryClient {
    @PostMapping("/task/categories")
    CategoryDTO createCategory(@RequestBody CreateCategoryDTO categoryDTO);


    @GetMapping("/task/categories/topCategories")
    List<CategoryDTO> getTopCategories();

    @GetMapping("/task/categories/subCategories/{categoryId}")
    List<CategoryDTO> getSubCategories(@PathVariable int categoryId);

    @GetMapping("/task/categories")
    ResponseEntity<List<CategoryDTO>> getCategories();
  
    @GetMapping("/task/categories/{categoryId}")
    CategoryDTO getCategoryById(@PathVariable int categoryId);

}
