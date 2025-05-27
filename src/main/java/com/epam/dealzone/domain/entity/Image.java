package com.epam.dealzone.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString(exclude = "product")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "image")
public class Image {
    @Id
    @Column(name = "image_uuid")
    private UUID uuid;
    @Column(name = "image_url")
    private String url;
    private boolean isPreview;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_uuid")
    private Product product;

    @PrePersist
    private void init(){
        if(uuid == null){
            uuid = UUID.randomUUID();
        }
    }
}
