package com.example.inventoryorderservice.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductCreateRequest {
    @NotBlank(message = "商品名称不能为空")
    @Size(max = 200, message = "商品名称不能超过200个字符")
    private String name;

    @NotNull(message = "价格不能为空")
    @Positive(message = "价格必须大于0")
    private BigDecimal price;

    @NotNull(message = "库存不能为空")
    @PositiveOrZero(message = "库存不能为负数")
    private Integer stock;

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
