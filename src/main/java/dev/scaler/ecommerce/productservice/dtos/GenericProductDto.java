package dev.scaler.ecommerce.productservice.dtos;

import dev.scaler.ecommerce.productservice.models.Price;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenericProductDto
{
    private String id;
    private String title;
    private PriceDto price;
    private CategoryDto category;
    private String description;
    private String image;
}
