package com.epam.dealzone.web.dto;

import com.epam.dealzone.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@Builder
public class ProductResponse {
    private final String uuid;
    private final String title;
    private final String description;
    private final BigDecimal price;
    private final List<String> images;
    private final LocalDateTime creationDate;

    public static ProductResponse toResponse(Product product){
        List<String> imageUrls = product.getImages()
                .stream().map(x -> x.getUrl())
                .toList();

        return ProductResponse.builder()
                .uuid(product.getUuid().toString())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .images(imageUrls)
                .creationDate(product.getCreationDate())
                .build();
    }
}