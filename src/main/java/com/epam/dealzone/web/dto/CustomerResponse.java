package com.epam.dealzone.web.dto;

import com.epam.dealzone.domain.entity.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private List<ProductResponse> favorites;

    public CustomerRequest toRequest() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setEmail(this.email);
        customerRequest.setName(this.name);
        customerRequest.setSurname(this.surname);
        customerRequest.setPhoneNumber(this.phoneNumber);
        customerRequest.setCity(this.city);
        return customerRequest;
    }

    public static CustomerResponse toResponse(Customer customer){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = customer.getCreationDate().format(formatter);

        List<ProductResponse> responses = customer.getProducts()
                .stream()
                .map(ProductResponse::toResponse)
                .toList();
        List<ProductResponse> favorites = customer.getFavouriteProducts()
                .stream()
                .map(ProductResponse::toResponse)
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
                .favorites(favorites)
                .build();
    }

}
