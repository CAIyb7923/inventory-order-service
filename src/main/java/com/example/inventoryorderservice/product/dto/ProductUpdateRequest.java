package com.example.inventoryorderservice.product.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductUpdateRequest {
    // 更新时字段可以为空（表示不修改），所以不加NotBlank/NotNull
    @Size(max = 200, message = "商品名称不能超过200个字符")
    private String name;

    @Positive(message = "价格必须大于0")
    private BigDecimal price;

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
}
