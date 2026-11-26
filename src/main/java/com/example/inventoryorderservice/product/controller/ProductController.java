package com.example.inventoryorderservice.product.controller;

import com.example.inventoryorderservice.common.PageResult;
import com.example.inventoryorderservice.common.Result;
import com.example.inventoryorderservice.product.dto.ProductCreateRequest;
import com.example.inventoryorderservice.product.dto.ProductResponse;
import com.example.inventoryorderservice.product.dto.ProductUpdateRequest;
import com.example.inventoryorderservice.product.model.Product;
import com.example.inventoryorderservice.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "创建商品")
    public Result<ProductResponse> create(@Valid @RequestBody ProductCreateRequest request) {
        return Result.success(productService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询商品详情")
    public Result<ProductResponse> getById(@PathVariable Long id) {
        return Result.success(productService.getById(id));
    }

    @GetMapping
    @Operation(summary = "商品分业列表")
    public Result<PageResult<ProductResponse>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return Result.success(productService.list(page, size, sortField, direction));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商品")
    public Result<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request
            ) {
        return Result.success(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return Result.success(null);
    }
}
