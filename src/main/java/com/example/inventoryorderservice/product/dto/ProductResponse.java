package com.example.inventoryorderservice.product.dto;

import com.example.inventoryorderservice.product.model.Product;

import java.math.BigDecimal;

public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;

    public ProductResponse() {}

    public ProductResponse(Long id, String name, BigDecimal price, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // 静态工厂方法，负责实体转DTO，以后字段多了只改这一处
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
