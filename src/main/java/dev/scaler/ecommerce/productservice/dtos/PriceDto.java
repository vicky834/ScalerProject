package dev.scaler.ecommerce.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceDto {
    String currency;
    double price;
}
