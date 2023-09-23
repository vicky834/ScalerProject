package dev.scaler.ecommerce.productservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.scaler.ecommerce.productservice.dtos.FakeStoreProductDto;
import dev.scaler.ecommerce.productservice.dtos.GenericProductDto;

@Service
public class FakeStoreProductService implements ProductService
{
    private final RestTemplate restTemplate;
    private String getProductRequestUrl = "https://fakestoreapi.com/products/{id}";
    private String genericProductUrl = "https://fakestoreapi.com/products";

    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder)
    {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override public List<GenericProductDto> getAllProducts()
    {
        ResponseEntity<List<FakeStoreProductDto>> responseEntity =
                restTemplate.exchange(
                        genericProductUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<FakeStoreProductDto>>()
                        {
                        });
        List<GenericProductDto> result = new ArrayList<>();
        if (responseEntity.getStatusCode().value() == HttpStatus.OK.value())
        {
            for (FakeStoreProductDto fakeStoreProductDto : Objects.requireNonNull(responseEntity.getBody()))
            {

            }
        }
        return result;
    }

    @Override public GenericProductDto getProductById(String id)
    {
        ResponseEntity<FakeStoreProductDto> response =
                restTemplate.getForEntity(getProductRequestUrl, FakeStoreProductDto.class, id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        GenericProductDto product = new GenericProductDto();
        product.setImage(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setTitle(fakeStoreProductDto.getTitle());
        /*product.setPrice(fakeStoreProductDto.getPrice());
        product.setCategory(fakeStoreProductDto.getCategory());*/
        //        response.getStatusCode()

        return product;
    }

    @Override public GenericProductDto deleteProductById(String id)
    {
        restTemplate.delete(getProductRequestUrl,id);
        return null;
    }

    @Override public GenericProductDto createProduct(GenericProductDto product)
    {
        ResponseEntity<GenericProductDto> response = restTemplate.postForEntity(
                genericProductUrl, product, GenericProductDto.class
        );

        return response.getBody();
    }

    @Override public GenericProductDto updateProductById(GenericProductDto genericProductDto, String id)
    {
        restTemplate.put(getProductRequestUrl,genericProductDto,id);
        return genericProductDto;
    }
}
