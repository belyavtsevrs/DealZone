package com.epam.dealzone.web.dto;

import com.epam.dealzone.domain.entity.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@Builder
public class CustomerResponse {
    private UUID uuid;
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
    private String avatar_url;
    private String creationDate;
    private String city;
    private List<ProductResponse> products;

    public static CustomerResponse toResponse(Customer customer){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = customer.getCreationDate().format(formatter);

        List<ProductResponse> responses = customer.getProducts()
                .stream()
                .map(x-> ProductResponse.toResponse(x))
                .toList();

        return CustomerResponse.builder()
                .uuid(customer.getUuid())
                .email(customer.getEmail())
                .name(customer.getName())
                .surname(customer.getSurname())
                .phoneNumber(customer.getPhoneNumber())
                .city(customer.getCity())
                .avatar_url(customer.getAvatar_url())
                .creationDate(date)
                .products(responses)
                .build();
    }
}
