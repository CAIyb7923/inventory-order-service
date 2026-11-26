package com.example.inventoryorderservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("库存订单服务API").description("商品与订单管理接口文档").version("1.0.0"));
    }
}
