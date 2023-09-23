package dev.scaler.ecommerce.productservice.controllers;

import java.util.List;

import dev.scaler.ecommerce.productservice.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.scaler.ecommerce.productservice.dtos.GenericProductDto;
import dev.scaler.ecommerce.productservice.service.ProductService;

@RestController
@RequestMapping ("/products")
public class ProductController
{
    private ProductService productService;

    public ProductController( ProductService productService)
    {
        this.productService = productService;
    }

    @GetMapping
    public List<GenericProductDto> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @GetMapping ("{id}")
    public GenericProductDto getProductById(@PathVariable ("id")String id) throws NotFoundException {
        return productService.getProductById(id);
    }

    @DeleteMapping ("{id}")
    public GenericProductDto deleteProductById(@PathVariable ("id")String id) throws NotFoundException {
       GenericProductDto genericProductDto =  productService.deleteProductById(id);
       return genericProductDto;
    }

    @PostMapping
    public GenericProductDto createProduct(@RequestBody GenericProductDto product)
    {
        return productService.createProduct(product);
    }

    @PutMapping ("{id}")
    public GenericProductDto updateProductById(@RequestBody GenericProductDto product, @PathVariable("id")String id) throws NotFoundException {
        return productService.updateProductById(product,id);
    }
}
