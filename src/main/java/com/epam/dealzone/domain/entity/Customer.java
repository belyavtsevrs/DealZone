package com.epam.dealzone.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
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
    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Image avatar_url;
    @Column(name = "is_active")
    private boolean active;
    @Column(name = "created_at")
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
