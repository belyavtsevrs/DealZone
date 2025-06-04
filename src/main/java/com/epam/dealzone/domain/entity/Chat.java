package com.epam.dealzone.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats")
public class Chat {

    @Id
    @Column(name = "chat_uuid")
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "buyer_uuid", referencedColumnName = "customer_uuid")
    private Customer buyer;
    @ManyToOne
    @JoinColumn(name = "seller_uuid", referencedColumnName = "customer_uuid")
    private Customer seller;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;

    @PrePersist
    public void init() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}

