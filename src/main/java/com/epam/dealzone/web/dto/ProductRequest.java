package com.epam.dealzone.web.dto;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.domain.entity.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;

@Getter
@Setter
@ToString
public class ProductRequest {
    @NotNull(message = "Title must not be null")
    @Size(min = 5, message = "Title has to contain at least 5 letters")
    private String title;
    @NotBlank(message = "Description must not be empty")
    private String description;
    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;
    private String principalName;
    private MultipartFile avatar;

    public Product toProduct() {
        return Product.builder()
                .title(this.title)
                .description(this.description)
                .price(this.price)
                .build();
    }
}
