package com.example.inventoryorderservice.order.controller;

import com.example.inventoryorderservice.common.PageResult;
import com.example.inventoryorderservice.common.Result;
import com.example.inventoryorderservice.order.dto.OrderCreateRequest;
import com.example.inventoryorderservice.order.dto.OrderResponse;
import com.example.inventoryorderservice.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "订单管理", description = "订单下单、查询、取消接口")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "创建订单")
    public Result<OrderResponse> create(@Valid @RequestBody OrderCreateRequest request) {
        return Result.success(
                orderService.create(request)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "订单详情")
    public Result<OrderResponse> getById(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }

    @GetMapping
    @Operation(summary = "订单分业列表")
    public Result<PageResult<OrderResponse>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return Result.success(orderService.list(page, size));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "取消订单")
    public Result<Void> cancel(@PathVariable Long id) {
        orderService.cancel(id);
        return Result.success(null);
    }
}
