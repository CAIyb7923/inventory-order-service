package com.example.inventoryorderservice.product.service;

import com.example.inventoryorderservice.common.BusinessException;
import com.example.inventoryorderservice.common.ErrorCode;
import com.example.inventoryorderservice.common.PageResult;
import com.example.inventoryorderservice.product.dto.ProductCreateRequest;
import com.example.inventoryorderservice.product.dto.ProductResponse;
import com.example.inventoryorderservice.product.dto.ProductUpdateRequest;
import com.example.inventoryorderservice.product.model.Product;
import com.example.inventoryorderservice.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        Product p = new Product(request.getName(), request.getPrice(), request.getStock());
        Product saved = productRepository.save(p);
        return ProductResponse.from(saved);
    }

    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        return ProductResponse.from(product);
    }

    public PageResult<ProductResponse> list(int page, int size, String sortField, String direction) {
        if (page < 1) {
            page = 1;
        }

        if (size < 1 || size > 100) {
            size = 10;
        }

        Sort sort = "desc".equalsIgnoreCase(direction)
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Product> result = productRepository.findAll(pageable);

        Page<ProductResponse> dtoPage = result.map(ProductResponse::from);
        return PageResult.of(dtoPage);
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        if (request.getName() != null && !request.getName().isBlank()) {
            product.setName(request.getName());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        Product saved = productRepository.save(product);

        return ProductResponse.from(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        productRepository.deleteById(id);
    }
}
