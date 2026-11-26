package com.example.inventoryorderservice.product.repository;

import com.example.inventoryorderservice.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> id(Long id);
}
