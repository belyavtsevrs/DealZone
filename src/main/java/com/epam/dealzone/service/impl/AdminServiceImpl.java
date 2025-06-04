package com.epam.dealzone.service.impl;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.repository.CustomerRepository;
import com.epam.dealzone.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {
    private final CustomerRepository customerRepository;

    public AdminServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void deleter(UUID uuid) {
        customerRepository.deleteById(uuid);
    }

    @Override
    public List<Customer> retrieve() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> retrieve(int page, int size) {
        return List.of();
    }

    @Override
    public Customer retrieve(UUID uuid) {
        return customerRepository.findById(uuid).get();
    }

    @Override
    public Customer retrieve(String name) {
        return null;
    }


    @Override
    public void saver(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void updater(Customer customer, UUID uuid) {
        Customer customerUpdated = Customer.builder()
                .uuid(uuid)
                .email(customer.getEmail())
                .password(customer.getPassword())
                .name(customer.getName())
                .surname(customer.getSurname())
                .phoneNumber(customer.getPhoneNumber())
                .role(customer.getRole())
                .products(customer.getProducts())
                .avatar_url(customer.getAvatar_url())
                .active(customer.isActive())
                .build();

        customerRepository.save(customerUpdated);
    }

    @Override
    public void banUser(UUID uuid) {
        Customer customer = customerRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Customer updatedCustomer = Customer.builder()
                .uuid(customer.getUuid())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .name(customer.getName())
                .surname(customer.getSurname())
                .phoneNumber(customer.getPhoneNumber())
                .role(customer.getRole())
                .products(customer.getProducts())
                .avatar_url(customer.getAvatar_url())
                .city(customer.getCity())
                .creationDate(customer.getCreationDate())
                .active(!customer.isActive())
                .build();

        customerRepository.save(updatedCustomer);
    }
}
