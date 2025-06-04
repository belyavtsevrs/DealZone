package com.epam.dealzone.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @Column(name = "message_uuid")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "chat_uuid", referencedColumnName = "chat_uuid")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_uuid", referencedColumnName = "customer_uuid")
    private Customer sender;

    @Column(name = "message_text", columnDefinition = "TEXT")
    private String messageText;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @PrePersist
    public void init() {
        if (sentAt == null) {
            sentAt = LocalDateTime.now();
        }
    }
}
