package dev.scaler.ecommerce.productservice.service;

import dev.scaler.ecommerce.productservice.dtos.CategoryDto;
import dev.scaler.ecommerce.productservice.dtos.GenericProductDto;
import dev.scaler.ecommerce.productservice.exceptions.NotFoundException;

import java.util.List;

public interface CategoryService {
    List<GenericProductDto> getAllProductsInCategory(String uuid) throws NotFoundException;
    List<CategoryDto> getAllCategory();
}