package com.example.inventoryorderservice.common;

// 错误码枚举
public enum ErrorCode {
    SUCCESS(0, "success"),
    BAD_REQUEST(400, "参数错误"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "系统繁忙，请稍后再试"),

    // 业务错误码
    // 商品模块1000段
    PRODUCT_NOT_FOUND(1001, "商品不存在"),
    PRODUCT_PRICE_INVALID(1002, "商品价格必须大于0"),

    // 订单模块2000段
    ORDER_NOT_FOUND(2001, "订单不存在"),
    ORDER_STOCK_NOT_ENOUGH(2002, "商品库存不足"),
    ORDER_ITEMS_EMPTY(2003, "订单明细不能为空"),
    ORDER_VERSION_CONFLICT(2004, "操作过于频繁，请重试");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
