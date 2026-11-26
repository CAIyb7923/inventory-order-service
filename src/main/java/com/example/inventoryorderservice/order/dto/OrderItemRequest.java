package com.example.inventoryorderservice.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderItemRequest {

    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @NotNull(message = "购买数量不能为空")
    @Positive(message = "购买数量必须大于0")
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
