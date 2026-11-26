package com.example.inventoryorderservice.order.dto;

import com.example.inventoryorderservice.order.model.OrderItem;

import java.math.BigDecimal;

public class OrderItemResponse {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;

    public static OrderItemResponse from(OrderItem item) {
        OrderItemResponse resp = new OrderItemResponse();
        resp.setProductId(item.getProductId());
        resp.setProductName(item.getProductName());
        resp.setPrice(item.getPrice());
        resp.setQuantity(item.getQuantity());
        resp.setSubtotal(item.getSubtotal());

        return resp;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
