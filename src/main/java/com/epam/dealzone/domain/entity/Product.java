package com.epam.dealzone.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_uuid")
    private UUID uuid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, length = 512)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;


    @PrePersist
    private void init() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
    }
}
