package com.epam.dealzone.web.dto;

import com.epam.dealzone.domain.entity.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@Getter
@ToString
@Builder
public class CustomerResponse {
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
    private String avatar_url;
    private String creationDate;

    public static CustomerResponse toResponse(Customer customer){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = customer.getCreationDate().format(formatter);

        return CustomerResponse.builder()
                .email(customer.getEmail())
                .name(customer.getName())
                .surname(customer.getSurname())
                .phoneNumber(customer.getPhoneNumber())
                .creationDate(date)
                .build();
    }
}
