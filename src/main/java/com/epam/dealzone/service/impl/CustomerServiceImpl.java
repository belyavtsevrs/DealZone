package com.epam.dealzone.service.impl;

import com.epam.dealzone.domain.entity.Customer;
import com.epam.dealzone.domain.entity.Image;
import com.epam.dealzone.domain.enums.Role;
import com.epam.dealzone.repository.CustomerRepository;
import com.epam.dealzone.service.CustomerService;
import com.epam.dealzone.web.dto.CustomerRequest;
import com.epam.dealzone.web.dto.CustomerResponse;
import com.epam.dealzone.web.dto.ProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String imageDir = "customerAvatars";

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void deleter(UUID uuid) {
        customerRepository.deleteById(uuid);
    }

    @Override
    public List<CustomerResponse> retrieve() {
        return List.of();
    }

    @Override
    public List<CustomerResponse> retrieve(int page, int size) {
        return List.of();
    }

    @Override
    public CustomerResponse retrieve(UUID uuid) {
        Customer customer = customerRepository.findById(uuid).orElseThrow(()->{
            throw new RuntimeException();
        });
        return null;
    }

    @Override
    public void saver(CustomerRequest request) {
        log.info("request = {}", request);
        Customer customer = Customer.builder()
                .email(request.getEmail())
                .name(request.getName())
                .surname(request.getSurname())
                .city(request.getCity())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .role(Set.of(Role.USER))
                .avatar_url("no-user-image.png")
                .build();

        log.info("customer = {}",customer);
        customerRepository.save(customer);
    }

    @Override
    public void updater(ProductRequest request, UUID uuid) {

    }
}
