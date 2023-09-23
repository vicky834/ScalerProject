package dev.scaler.ecommerce.productservice.service;

import dev.scaler.ecommerce.productservice.dtos.CategoryDto;
import dev.scaler.ecommerce.productservice.dtos.GenericProductDto;
import dev.scaler.ecommerce.productservice.dtos.PriceDto;
import dev.scaler.ecommerce.productservice.exceptions.NotFoundException;
import dev.scaler.ecommerce.productservice.models.Category;
import dev.scaler.ecommerce.productservice.models.Product;
import dev.scaler.ecommerce.productservice.repositories.CategoryRepository;
import dev.scaler.ecommerce.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class CategoryServiceImpl implements CategoryService{

    CategoryRepository categoryRepository;

    ProductRepository productRepository;

    public CategoryServiceImpl(ProductRepository productRepository,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<GenericProductDto> getAllProductsInCategory(String uuid) throws NotFoundException {
        UUID id = UUID.fromString(uuid);
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()){
            throw new NotFoundException("Category is not found with given Id");
        }
        Category category = categoryOptional.get();

        List<Product> productList = productRepository.findAllByCategoryEquals(category);
        List<GenericProductDto> productDtoList = new ArrayList<>();

        for (Product product : productList) {
            GenericProductDto genericProductDto = getGenericProductDto(product);
            productDtoList.add(genericProductDto);
        }
        return productDtoList;
    }

    private GenericProductDto getGenericProductDto(Product product) {

        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setId(product.getId().toString());
        genericProductDto.setTitle(product.getTitle());
        genericProductDto.setDescription(product.getDescription());
        genericProductDto.setImage(product.getImage());
        PriceDto priceDto = new PriceDto();
        priceDto.setPrice(product.getPrice().getPrice());
        priceDto.setCurrency(product.getPrice().getCurrency());
        genericProductDto.setPrice(priceDto);
        return genericProductDto;
    }

    @Override
    public List<CategoryDto> getAllCategory() {


        List<Category> categoryList = categoryRepository.findAll();

        List<CategoryDto> categoryDtoList = new ArrayList<>();

        categoryList.forEach(category -> {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(category.getName());
            categoryDto.setId(category.getId().toString());
            categoryDtoList.add(categoryDto);
        });
        return categoryDtoList;
    }
}
