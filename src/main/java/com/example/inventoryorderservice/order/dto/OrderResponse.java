package com.example.inventoryorderservice.order.dto;

import com.example.inventoryorderservice.order.model.Order;
import com.example.inventoryorderservice.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private String orderNo;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createTime;
    private List<OrderItemResponse> items;

    public static OrderResponse from(Order order) {
        OrderResponse resp = new OrderResponse();
        resp.setId(order.getId());
        resp.setOrderNo(order.getOrderNo());
        resp.setStatus(order.getStatus());
        resp.setTotalAmount(order.getTotalAmount());
        resp.setCreateTime(order.getCreateTime());
        resp.setItems(
                order.getItems().stream().map(OrderItemResponse::from).toList()
        );

        return resp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}
