package com.epam.dealzone.web.dto;

import com.epam.dealzone.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class ProductResponse {
    private final String title;
    private final String description;
    private final BigDecimal price;
    private final LocalDateTime creationDate;

    public static ProductResponse fromProduct(Product product){
        return ProductResponse.builder()
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .creationDate(product.getCreationDate())
                .build();
    }
}