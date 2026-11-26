package com.example.inventoryorderservice.order.service;

import com.example.inventoryorderservice.common.BusinessException;
import com.example.inventoryorderservice.common.ErrorCode;
import com.example.inventoryorderservice.common.PageResult;
import com.example.inventoryorderservice.order.dto.OrderCreateRequest;
import com.example.inventoryorderservice.order.dto.OrderItemRequest;
import com.example.inventoryorderservice.order.dto.OrderResponse;
import com.example.inventoryorderservice.order.model.Order;
import com.example.inventoryorderservice.order.model.OrderItem;
import com.example.inventoryorderservice.order.model.OrderStatus;
import com.example.inventoryorderservice.order.repository.OrderRepository;
import com.example.inventoryorderservice.product.model.Product;
import com.example.inventoryorderservice.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponse create(OrderCreateRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BusinessException(ErrorCode.ORDER_ITEMS_EMPTY);
        }

        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setStatus(OrderStatus.CREATED);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

            if (product.getStock() < itemReq.getQuantity()) {
                throw new BusinessException(
                        ErrorCode.ORDER_STOCK_NOT_ENOUGH,
                        "商品[" + product.getName() + "]库存不足，当前库存：" + product.getStock()
                );
            }

            // 扣库存，version乐观锁在这里生效
            product.setStock(product.getStock() - itemReq.getQuantity());
            productRepository.save(product);

            OrderItem item = new OrderItem(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    itemReq.getQuantity()
            );
            items.add(item);
            totalAmount = totalAmount.add(item.getSubtotal());
        }

        order.setTotalAmount(totalAmount);
        items.forEach(order::addItem);

        try {
            Order saved = orderRepository.save(order);
            return OrderResponse.from(saved);
        } catch (ObjectOptimisticLockingFailureException e) {
            // 乐观锁冲突，整个事务回滚，库存不会被扣
            throw new BusinessException(ErrorCode.ORDER_VERSION_CONFLICT);
        }
    }

    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        return OrderResponse.from(order);
    }

    public PageResult<OrderResponse> list(int page, int size) {
        if (page < 1) {
            page = 1;
        }

        if (size < 1 || size >= 100) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createTime").descending());
        Page<Order> result = orderRepository.findAll(pageable);
        Page<OrderResponse> dtoPage = result.map(OrderResponse::from);

        return PageResult.of(dtoPage);
    }

    @Transactional
    public void cancel(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() == OrderStatus.PAID) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "已支付订单不能取消");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            return;
        }

        // 回滚库存
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private String generateOrderNo() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int random = ThreadLocalRandom.current().nextInt(100000, 999999);

        return datePart + random;
    }
}
