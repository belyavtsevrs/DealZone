package com.epam.dealzone.web.dto;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@Builder
public class ProductResponse {
    private final String uuid;
    private final String title;
    private final String description;
    private final BigDecimal price;
    private final List<String> images;
    private String creationDate;
    private UUID customerUUID;
    private String emailOwner;

    public static ProductResponse toResponse(Product product){
        List<String> imageUrls = product.getImages()
                .stream().map(x -> x.getUrl())
                .toList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = product.getCreationDate().format(formatter);
        Customer owner = product.getCustomer();

        return ProductResponse.builder()
                .uuid(product.getUuid().toString())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .images(imageUrls)
                .creationDate(date)
                .customerUUID(owner.getUuid())
                .emailOwner(owner.getEmail())
                .build();
    }

    public ProductRequest toRequest(){
        ProductRequest request = new ProductRequest();
        request.setTitle(this.title);
        request.setDescription(this.description);
        request.setPrice(this.price);
        request.setPrincipalName(this.emailOwner);
        request.setDescription(this.description);
        return request;
    }
}