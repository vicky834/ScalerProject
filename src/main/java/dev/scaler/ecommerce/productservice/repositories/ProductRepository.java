package dev.scaler.ecommerce.productservice.repositories;

import dev.scaler.ecommerce.productservice.models.Category;
import dev.scaler.ecommerce.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, Repository<Product,UUID> {

    List<Product> findAllByCategoryEquals(Category category);
}
