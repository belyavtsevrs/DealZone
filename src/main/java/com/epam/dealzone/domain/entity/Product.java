package com.epam.dealzone.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "product")
public class Product {
    @Id
    @EqualsAndHashCode.Include
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Image> images = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_uuid")
    @ToString.Exclude
    private Customer customer;

    @ManyToMany(mappedBy = "favouriteProducts")
    @ToString.Exclude
    private List<Customer> likedByCustomers = new ArrayList<>();

    @PrePersist
    private void init() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
    }

    public void addImage(Image image){
        this.images.add(image);
        image.setProduct(this);
    }
}
