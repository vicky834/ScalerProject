package dev.scaler.ecommerce.productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private String title;
    private String description;
    private String image;
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(name = "category")
    private Category category;
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Price price;
}