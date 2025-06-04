package com.epam.dealzone.domain.entity;

import com.epam.dealzone.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customer_uuid")
    private UUID uuid;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "city")
    private String city;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "avatar_url")
    private String avatar_url;
    @Column(name = "is_active")
    private boolean active;
    @Column(name = "created_at")
    private LocalDateTime creationDate;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products;

    @ElementCollection(
            targetClass = Role.class,
            fetch = FetchType.EAGER
    )
    @CollectionTable(
            name = "customer_role",
            joinColumns = @JoinColumn(name = "customer_uuid")
    )
    @Enumerated(EnumType.STRING)
    private Set<Role> role;

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
