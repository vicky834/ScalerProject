package dev.scaler.ecommerce.productservice.controllers;

import dev.scaler.ecommerce.productservice.dtos.CategoryDto;
import dev.scaler.ecommerce.productservice.dtos.GenericProductDto;
import dev.scaler.ecommerce.productservice.exceptions.NotFoundException;
import dev.scaler.ecommerce.productservice.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("{id}")
    public List<GenericProductDto> getAllProductsInCategory(@PathVariable ("id") String uuid) throws NotFoundException {
        return categoryService.getAllProductsInCategory(uuid);
    }

    @GetMapping
    public List<CategoryDto> getAllCategory() {
        return categoryService.getAllCategory();
    }
}
