package dev.scaler.ecommerce.productservice.service;

import java.util.List;

import dev.scaler.ecommerce.productservice.dtos.GenericProductDto;
import dev.scaler.ecommerce.productservice.exceptions.NotFoundException;

public interface ProductService
{
    List<GenericProductDto> getAllProducts();

    GenericProductDto getProductById(String id) throws NotFoundException;

    GenericProductDto deleteProductById(String id) throws NotFoundException;

    GenericProductDto createProduct(GenericProductDto product);

    GenericProductDto updateProductById(GenericProductDto genericProductDto, String id) throws NotFoundException;
}
