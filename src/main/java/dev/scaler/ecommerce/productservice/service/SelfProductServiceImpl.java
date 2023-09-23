package dev.scaler.ecommerce.productservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.scaler.ecommerce.productservice.dtos.CategoryDto;
import dev.scaler.ecommerce.productservice.dtos.PriceDto;
import dev.scaler.ecommerce.productservice.exceptions.NotFoundException;
import dev.scaler.ecommerce.productservice.models.Category;
import dev.scaler.ecommerce.productservice.models.Price;
import dev.scaler.ecommerce.productservice.models.Product;
import dev.scaler.ecommerce.productservice.repositories.CategoryRepository;
import dev.scaler.ecommerce.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import dev.scaler.ecommerce.productservice.dtos.GenericProductDto;

@Primary
@Service
public class SelfProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    public SelfProductServiceImpl(ProductRepository productRepository,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<GenericProductDto> productDtoList = new ArrayList<>();

        for (Product product : productList) {
            GenericProductDto genericProductDto = getGenericProductDto(product);
            productDtoList.add(genericProductDto);
        }
        return productDtoList;
    }

    @Override
    public GenericProductDto getProductById(String id) throws NotFoundException {
        UUID uuid = UUID.fromString(id);
        Optional<Product> productOptional = productRepository.findById(uuid);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return getGenericProductDto(product);
        }
        throw new NotFoundException("Product is not found with given Id");
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
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(product.getCategory().getName());
        genericProductDto.setCategory(categoryDto);
        return genericProductDto;
    }

    @Override
    public GenericProductDto deleteProductById(String id) throws NotFoundException {
        UUID uuid = UUID.fromString(id);
        Optional<Product> productOptional = productRepository.findById(uuid);

        if (productOptional.isPresent()) {
            productRepository.deleteById(uuid);
            Product product = productOptional.get();
            return getGenericProductDto(product);
        }
        throw new NotFoundException("Product is not found with given Id");

    }

    @Override
    public GenericProductDto createProduct(GenericProductDto product) {
        Product saveProduct = new Product();
        Category category = new Category();
        category.setName(product.getCategory().getName());
        saveProduct.setCategory(category);
        saveProduct.setTitle(product.getTitle());
        saveProduct.setDescription(product.getDescription());
        saveProduct.setImage(product.getImage());
        Price price = new Price();
        price.setCurrency(product.getPrice().getCurrency());
        price.setPrice(product.getPrice().getPrice());
        saveProduct.setPrice(price);
        saveProduct = productRepository.save(saveProduct);

        product.setId(saveProduct.getId().toString());
        return product;
    }

    @Override
    public GenericProductDto updateProductById(GenericProductDto genericProductDto, String id) throws NotFoundException {
        UUID uuid = UUID.fromString(id);
        Optional<Product> productOptional = productRepository.findById(uuid);
        if (productOptional.isEmpty()) {
            throw new NotFoundException("Product is not found with given Id");
        }
        Product saveProduct = productOptional.get();
        Category category = categoryRepository.findByName(genericProductDto.getCategory().getName());
        if (category == null) {
            category = new Category();
            category.setName(genericProductDto.getCategory().getName());
        }
        saveProduct.setCategory(category);
        saveProduct.setTitle(genericProductDto.getTitle());
        saveProduct.setDescription(genericProductDto.getDescription());
        saveProduct.setImage(genericProductDto.getImage());
        Price price = new Price();
        price.setCurrency(genericProductDto.getPrice().getCurrency());
        price.setPrice(genericProductDto.getPrice().getPrice());
        saveProduct.setPrice(price);
        productRepository.save(saveProduct);

        genericProductDto.setId(saveProduct.getId().toString());
        return genericProductDto;
    }
}
